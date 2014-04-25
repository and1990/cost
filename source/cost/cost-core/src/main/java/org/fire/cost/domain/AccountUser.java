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
@Table(name = "cost_account_user", catalog = "cost")
public class AccountUser implements java.io.Serializable {

    private Long accountUserId;
    private User user;
    private Account account;


    public AccountUser() {
    }


    public AccountUser(Long accountUserId, User user, Account account) {
        this.accountUserId = accountUserId;
        this.user = user;
        this.account = account;
    }

    @Id
    @Column(name = "account_user_id", unique = true, nullable = false)
    public Long getAccountUserId() {
        return this.accountUserId;
    }

    public void setAccountUserId(Long accountUserId) {
        this.accountUserId = accountUserId;
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
    @JoinColumn(name = "account_id", nullable = false)
    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account costAccount) {
        this.account = costAccount;
    }

}