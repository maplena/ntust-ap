package com.example.ntust_ap_server.lottery;

import lombok.AllArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.ntust_ap_server.activity.ImageRequestHandler;
import com.example.ntust_ap_server.lottery.Item.AvailableTicketItem;
import com.example.ntust_ap_server.lottery.Item.LotteryItem;
import com.example.ntust_ap_server.lottery.Repository.UnAvailableTicketRepository;
import com.example.ntust_ap_server.lottery.Repository.AvailableTicketRepository;
import com.example.ntust_ap_server.lottery.Repository.LotteryRepository;

import com.example.ntust_ap_server.lottery.Item.UnAvailableTicketItem;
import com.example.ntust_ap_server.lottery.Request.GetLotteryRequest;
import com.example.ntust_ap_server.lottery.Request.LotteryParticipentRequest;
// import com.example.ntust_ap_server.lottery.Request.GetLotteryRequest;
// import com.example.ntust_ap_server.lottery.Request.ParticipentRequest;
import com.example.ntust_ap_server.lottery.Request.LotteryRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/*抽獎的服務 */

@Service
@AllArgsConstructor
public class LotteryService {
    //
    private final LotteryRepository lotteryRepository;
    private final AvailableTicketRepository availableTicketRepository;
    private final UnAvailableTicketRepository unAvailableTicketRepository;
    private static final String RUNING = "running";
    private static final String END = "end";
    private static final String UPLOADED_FOLDER = "images/lotteryphoto";

    /* 當前抽獎------------------------------------------------------------------- */

    /**
     * 新增抽獎
     * 
     * @param request 要求
     * @param img     圖片
     * @return
     */
    public String addLottery(LotteryRequest request, MultipartFile img) {
        try {
            LotteryItem lotteryItem = new LotteryItem(request);
            String imagePath = "";
            if (img != null) {
                lotteryRepository.save(lotteryItem);
                imagePath = saveLotteryPhoto(img, lotteryItem.getId());
                lotteryItem.setImage_path(imagePath);
            } else {
                lotteryItem.setImage_path(imagePath);
            }
            lotteryRepository.saveAndFlush(lotteryItem);
            return "成功";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "失敗";
        }

        // 回傳該抽獎的主辦人ID
    }

    /**
     * 存取該抽獎
     * 
     * @param lotteryId 抽獎Id
     * @return
     */
    public Optional<LotteryItem> getLottery(String lotteryId) {
        return lotteryRepository.findById(Long.parseLong(lotteryId));
    }

    /**
     * 更新抽獎
     * 
     * @param request
     * @param img
     * @return
     */
    public String updateLottery(LotteryRequest request, MultipartFile img) {
        if (request.getHostID().equals(request.getRequestUserId())) {
            if (img == null) {
                lotteryRepository.saveAndFlush(new LotteryItem(request));
            } else {
                try {
                    String imagePath = saveLotteryPhoto(img, request.getId());
                    request.setImage_path(imagePath);
                    lotteryRepository.saveAndFlush(new LotteryItem(request));
                } catch (Exception e) {
                    return "失敗";
                }
            }

            return "成功";
        } else
            return "失敗";
    }

    /**
     * 
     * @param userId    使用者ID
     * @param lotteryId 活動ID (string)
     * @return 成功時 回傳成功 失敗時 回傳失敗
     */
    public String stopLottery(String userId, Long lotteryId) {

        // 先確認該user是否為host
        if (checkLotteryHost(userId, lotteryId)) {
            try {
                /** 將所有參與人員資料 移動至 參與歷史資料庫 */
                LinkedList<AvailableTicketItem> list = availableTicketRepository
                        .getAlluserByLotteryId(lotteryId);
                for (AvailableTicketItem c : list) {
                    UnAvailableTicketItem item = new UnAvailableTicketItem(c);
                    unAvailableTicketRepository.save(item);
                }
                unAvailableTicketRepository.flush();

                LotteryItem lotteryItem = lotteryRepository.getById(lotteryId);
                lotteryItem.setStatus(END);
                lotteryRepository.save(lotteryItem);
                lotteryRepository.flush();

                availableTicketRepository.deleteAllInBatch(list);
                availableTicketRepository.flush();// 立即更新

                return "成功";

            } catch (Exception e) {
                e.printStackTrace();
                return "失敗，發生錯誤。";
            }

        } else {
            return "失敗";
        }

        // // 是promoter
        // // 執行下架活動
        // // 將活動屬性 "狀態" 改為stop
        // // 回傳完成
        // // 不是promoter
        // // 回傳失敗

        // 轉移參加資料 從current到past資料庫

        // TODO通知所有user該活動被下架

    }

    /**
     * 參加抽獎
     * 
     * @param request
     * @return 抽中 or 沒抽中 or 失敗
     */
    public String joinLottery(LotteryParticipentRequest request) {
        // 確認活動運作中
        LotteryItem lotteryItem = lotteryRepository.getById(request.getLotteryId());
        if (checkLotteryStatus(request.getLotteryId(), RUNING)) {

            Float p = lotteryRepository.getProbabilityByid(request.getLotteryId());
            Random random = new Random();
            if (random.nextFloat() <= p) {
                // 抽中

                AvailableTicketItem ticket = new AvailableTicketItem(
                        request);
                ticket.setTitle(lotteryItem.getTitle());
                ticket.setDescription(lotteryItem.getDescription());
                ticket.setEnd_date(lotteryItem.getEnd_date());
                ticket.setHost(lotteryItem.getHost());
                Date date = new Date();
                String datestring = String.valueOf(date.getTime());
                ticket.setHash_sequence(hash(request.getLotteryId().toString() + request.getUserId() + datestring));
                availableTicketRepository.save(ticket);
                return "成功，恭喜抽中";
            } else {
                return "成功，沒有抽中";
            }
        } else {
            return "失敗，該抽獎已結束";
        }

    }

    public String useLottery(String hashSequence) {
        AvailableTicketItem ticket = availableTicketRepository.findByHashSequence(hashSequence);
        if (ticket == null) {
            return "兌換券不可用/已過期";
        } else {
            unAvailableTicketRepository.saveAndFlush(new UnAvailableTicketItem(ticket));// 寫進已使用票券
            availableTicketRepository.delete(ticket);// 從當前票券刪除
            availableTicketRepository.flush();// 更新當前票券 資料庫
            return "使用成功";

        }
    }

    public String hash(String password) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] passBytes = password.getBytes();
            byte[] passHash = sha256.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < passHash.length; i++) {
                sb.append(Integer.toString((passHash[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Resource
    private ImageRequestHandler imageRequestHandler;

    public void getLotteryPhoto(String lotteryId, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        Path path = Paths.get(UPLOADED_FOLDER, lotteryId + ".jpg");
        File file = new File(path.toAbsolutePath().toString());
        httpServletRequest.setAttribute(ImageRequestHandler.ATTRIBUTE_FILE, file);
        imageRequestHandler.handleRequest(httpServletRequest, httpServletResponse);
    }

    // 移除抽獎人員
    public String removeLotteryParticipent(AvailableTicketItem ticket) {
        try {
            AvailableTicketItem a = availableTicketRepository.findByLotteryIdandUserId(
                    ticket.getLotteryId(), ticket.getUserId());
            availableTicketRepository.delete(a);

            return "成功";
        } catch (Exception exception) {
            exception.getStackTrace();
            return "失敗";
        }
    }

    /**
     * 取得當前抽獎參與者列表
     * 
     * @param lotteryId
     * @param page
     * @return
     */
    public List<AvailableTicketItem> getCurrentLotteryParticipentListByLotteryId(String lotteryId, String page) {
        Long lotteryidlong = Long.parseLong(lotteryId);// 將ID轉換為Long
        int pagenumber = Integer.parseInt(page);
        int pagesize = 20;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);

        return availableTicketRepository.findByLotteryId(lotteryidlong, pagereq).getContent();
    }

    /**
     * 取得已參與的抽獎列表
     * 
     * @param userId
     * @param page
     * @return
     */
    public List<AvailableTicketItem> getMyCurrentLotterys(String userId, String page) {

        int pagenumber = Integer.parseInt(page);
        int pagesize = 20;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);
        return availableTicketRepository.findByUserId(userId, pagereq)
                .getContent();
    }

    /* 取得抽獎列表------------------------------------------------------------------- */

    /**
     * 取得抽獎列表 已限制內容
     * 
     * @param request
     * @return
     */
    public List<LotteryItem> getLotterylist(GetLotteryRequest request) {
        /** 取得活動列表(by type) */
        int pagenumber = request.getPage();
        int pagesize = 10;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);
        return lotteryRepository.findAvailable(RUNING, pagereq).getContent();
    }

    // 取得抽獎列表(by promoter)
    public List<LotteryItem> getLotterylistbypromoter(String promoter, String page, String status) {
        int pagenumber = Integer.parseInt(page);
        int pagesize = 10;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);

        return lotteryRepository.findByHostId(promoter, status, pagereq).getContent();
    }

    /* 歷史抽獎------------------------------------------------------------------- */

    /**
     * 取得歷史抽獎參與者列表
     * 
     * @param lotteryId
     * @param page
     * @return
     */
    public List<UnAvailableTicketItem> getPastLotteryParticipentListByLotteryId(String lotteryId,
            String page) {
        Long lotteryidlong = Long.parseLong(lotteryId);// 將ID轉換為Long
        int pagenumber = Integer.parseInt(page);
        int pagesize = 20;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);
        return unAvailableTicketRepository.findByLotteryId(lotteryidlong, pagereq).getContent();

    }

    /**
     * 取得活動歷史紀錄
     * 
     * @param userId
     * @param page
     * @return
     */
    public List<UnAvailableTicketItem> getMyPastLotterys(String userId, String page) {

        int pagenumber = Integer.parseInt(page);
        int pagesize = 20;
        Pageable pagereq = PageRequest.of(pagenumber, pagesize).withPage(pagenumber);

        return unAvailableTicketRepository.findByUserId(userId, pagereq)
                .getContent();

    }

    /** 取得抽獎數量 */

    /* 確認用方法------------------------------------------------------------------- */

    /**
     * 確認活動狀態
     * 
     * @param lotteryId
     * @return 相符時 回傳true 否則false
     */
    public boolean checkLotteryStatus(Long lotteryId, String status) {
        return lotteryRepository.getStatusByid(lotteryId).equals(status);
    }

    /**
     * 確認是否為主辦者
     * 
     * @param requestUserId 請求者ID
     * @param lotteryId     活動ID
     * @return true or false
     */
    public boolean checkLotteryHost(String requestUserId, Long lotteryId) {
        return lotteryRepository.getHostByid(lotteryId).equals(requestUserId);
    }

    /* 上傳* */

    public String saveLotteryPhoto(MultipartFile img, Long id) throws IOException, IllegalStateException {
        Path path = Paths.get(UPLOADED_FOLDER, Long.toString(id) + getExtension(img));
        img.transferTo(new File(path.toAbsolutePath().toString()));
        return path.toAbsolutePath().toString();
    }

    /**
     * 回傳副檔名 含"."
     * 
     * @param file
     * @return
     */
    private String getExtension(MultipartFile file) throws NullPointerException {
        String originalFilename = file.getOriginalFilename();
        return originalFilename.substring(originalFilename.lastIndexOf('.'));
    }

}
