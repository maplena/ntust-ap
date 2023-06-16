package com.example.ntust_ap_server.lottery;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.ntust_ap_server.lottery.Item.AvailableTicketItem;
import com.example.ntust_ap_server.lottery.Item.LotteryItem;
import com.example.ntust_ap_server.lottery.Request.GetLotteryRequest;
import com.example.ntust_ap_server.lottery.Request.LotteryParticipentRequest;
import com.example.ntust_ap_server.lottery.Request.LotteryRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping(path = "api/v1/lottery", produces = MediaType.APPLICATION_JSON_VALUE) // 設定 URL path
@AllArgsConstructor
public class LotteryController {
    private final LotteryService lotteryService;


    @PostMapping(value = "/add", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE })
    public String addLottery(@RequestPart(name = "img", required = false) MultipartFile img,
            @RequestPart(name = "request") LotteryRequest lotteryRequest) {
        return lotteryService.addLottery(lotteryRequest, img);
    }

    @PostMapping(value = "/update", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE,
            MediaType.APPLICATION_JSON_VALUE })
    public String updateLottery(@RequestPart(name = "img", required = false) MultipartFile img,
            @RequestPart(name = "request") LotteryRequest lotteryRequest) {
        return lotteryService.updateLottery(lotteryRequest, img);
    }


    @GetMapping("/get/{id}")
    public Optional<LotteryItem> getLottery(@PathVariable("id") String id) {
        return lotteryService.getLottery(id);
    }

    @PostMapping(value = "/draw")
    public String drawLottery(@RequestBody LotteryParticipentRequest request) {
        return lotteryService.joinLottery(request);
    }

    @GetMapping("/use/{hash_sequence}")
    public String useLottery(@PathVariable("hash_sequence") String hash_sequence) {
        try {
            return lotteryService.useLottery(hash_sequence);
        } catch (Exception e) {
            e.printStackTrace();
            return "失敗";
        }

    }

    @GetMapping("/getPhoto/{id}")
    public void getLotteryPhoto(@PathVariable("id") String lotteryId, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        try {
            lotteryService.getLotteryPhoto(lotteryId, httpServletRequest, httpServletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @PostMapping("/getbylimit")
    public List<LotteryItem> getSurveyByLimit(@RequestBody GetLotteryRequest request) {
        return lotteryService.getLotterylist(request);
    }

    @GetMapping("/getmylottery/current/{userId}/{page}")
    public List<AvailableTicketItem> getmyCurrentSurveyByuserId(
            @PathVariable("userId") String userId,
            @PathVariable("page") String page) {
        return lotteryService.getMyCurrentLotterys(userId, page);
    }    


}
