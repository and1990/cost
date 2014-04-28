package org.fire.cost.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 注释：
 * 时间：2014年04月25日 上午11:37
 * 作者：刘腾飞
 */
@Entity
@Table(name = "cost_group_user", catalog = "cost")
public class AccountUser implements java.io.Serializable {

    private Long groupUserId;
    private User user;
    private Group group;


    public AccountUser() {
    }


    public AccountUser(Long groupUserId, User user, Group group) {
        this.groupUserId = groupUserId;
        this.user = user;
        this.group = group;
    }

    @Id
    @Column(name = "account_user_id", unique = true, nullable = false)
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
    public Group getAccount() {
        return this.group;
    }

    public void setAccount(Group group) {
        this.group = group;
    }

}