package org.fire.cost.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 注释：组、用户关系实体
 * 时间：2014年04月25日 上午11:37
 * 作者：刘腾飞
 */
@Entity
@Table(name = "cost_group_user", catalog = "cost")
public class GroupUser implements java.io.Serializable {

    private Long groupUserId;
    private User user;
    private Group group;


    public GroupUser() {
    }


    public GroupUser(Long groupUserId, User user, Group group) {
        this.groupUserId = groupUserId;
        this.user = user;
        this.group = group;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "group_user_id", unique = true, nullable = false)
    public Long getGroupUserId() {
        return this.groupUserId;
    }

    public void setGroupUserId(Long accountUserId) {
        this.groupUserId = accountUserId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return this.user;
    }

    public void setUser(User costUser) {
        this.user = costUser;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}