package com.example.newsfeed.follow.entity;

import com.example.newsfeed.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.example.newsfeed.global.entity.BaseDateTime;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "follows",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"follower_id", "following_id"})
        }
)
public class Follow extends BaseDateTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private Member followerMember;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private Member followingMember;

    @Enumerated(EnumType.STRING)
    private FollowStatus followStatus;

    public Follow(Member followerMember, Member followingMember) {
        this.followerMember = followerMember;
        this.followingMember = followingMember;
        this.followStatus = FollowStatus.FOLLOWING;
    }

    public void updateFollowStatus(FollowStatus followStatus) {
        this.followStatus = followStatus;
    }
}