package com.noron.core;

import com.hm.socialmedia.tables.pojos.User;
import com.hm.socialmedia.tables.records.UserRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class UserRepoImpl implements IUserRepo {
    private final DSLContext dslContext;

    public UserRepoImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    private com.hm.socialmedia.tables.User users = com.hm.socialmedia.tables.User.USER;

    @Override
    public User getUserById(Integer id) {
        return dslContext.selectOne()
                .from(users)
                .where(users.ID.eq(id))
                .fetchOneInto(User.class);
    }

    @Override
    public boolean insertUser(User user) {
        return dslContext.insertInto(users, users.ACCOUNT, users.NAME, users.CREATED_AT
                        , users.PASSWORD, users.EMAIL
                        , users.AVATAR, users.AUTHORITY)
                .values(user.getAccount(), user.getName(), LocalDateTime.now(),
                        user.getPassword(), user.getEmail(), user.getAvatar(), user.getAuthority())
                .returning(users.ID)
                .execute() == 1;
    }

    @Override
    public User updateUser(User user, Integer id) {
        dslContext.update(users)
                .set(users.ACCOUNT, user.getAccount())
                .set(users.AUTHORITY, user.getAuthority())
                .set(users.AVATAR, user.getAvatar())
                .set(users.EMAIL, user.getEmail())
                .set(users.PASSWORD, user.getPassword())
                .set(users.NAME, user.getName())
                .set(users.UPDATED_AT, LocalDateTime.now())
                .where(users.ID.eq(id))
                .execute();
        return getUserById(id);
    }

    @Override
    public User deleteUser(Integer id) {
        dslContext.update(users)
                .set(users.DELETED_AT, LocalDateTime.now())
                .where(users.ID.eq(id))
                .execute();
        return getUserById(id);
    }

    @Override
    public User findUserByUserAccount(String userAccount) {
        return dslContext.select()
                .from(users)
                .where(users.ACCOUNT.eq(userAccount))
                .fetchOneInto(User.class);
    }
}
