package com.example.ntust_ap_server.activity.Item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "activities_rating")
public class ActivityRatingItem {
    @SequenceGenerator(name = "activity_rating_item_sequence", sequenceName = "activity_rating_item_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_rating_item_sequence")
    private Long id;

    private Long activityId;
    private String userId;
    private int rating;
    private String comment;

    public ActivityRatingItem(Long activityId, String userId, int rating,String comment){
        this.activityId = activityId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }
    
    public ActivityRatingItem(Long id,Long activityId, String userId, int rating, String comment ) {
        this.id=id;
        this.activityId=activityId;
        this.userId=userId;
        this.rating=rating;
        this.comment = comment;
    }
}
