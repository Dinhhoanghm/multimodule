package com.noron.core;

import com.hm.socialmedia.tables.pojos.Comment;
import com.hm.socialmedia.tables.pojos.Post;
import com.hm.socialmedia.tables.records.CommentRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public class CommentRepoImpl implements ICommentRepo{
    private final DSLContext dslContext;
    private IPostRepo postRepo;

    public CommentRepoImpl(DSLContext dslContext, IPostRepo postRepo) {
        this.dslContext = dslContext;
        this.postRepo = postRepo;
    }

    private com.hm.socialmedia.tables.Comment comments = com.hm.socialmedia.tables.Comment.COMMENT;

    @Override
    public Comment getCommentById(Integer id) {
        return dslContext.selectOne()
                .from(comments)
                .where(comments.ID.eq(id))
                .fetchOneInto(Comment.class);
    }

    @Override
    public List<Comment> getAllCommentsByCommon(Integer postId) {
        return dslContext.select()
                .from(comments)
                .where(comments.POST_ID.eq(postId))
                .orderBy(comments.VOTE_COUNT)
                .fetchInto(Comment.class);
    }

    @Override
    public List<Comment> getAllCommentByDate(Integer postId) {
        return dslContext.select()
                .from(comments)
                .where(comments.POST_ID.eq(postId))
                .orderBy(comments.CREATED_AT)
                .fetchInto(Comment.class);
    }

    @Override
    public Comment updateComment(Comment comment, Integer id) {
        dslContext.update(comments)
                .set(comments.CONTENT,comment.getContent())
                .set(comments.UPDATED_AT,LocalDateTime.now())
                .where(comments.ID.eq(id))
                .execute();
        return getCommentById(id);

    }

    @Override
    public Comment deleteComment(Integer id) {
         dslContext.update(comments)
                .set(comments.DELETED_AT,LocalDateTime.now())
                .where(comments.ID.eq(id))
                .execute();
         return getCommentById(id);
    }

    @Override
    public Comment insertComment(Comment comment) {
        Post post =postRepo.getPostById(comment.getPostId());
        CommentRecord commentsRecord = dslContext.insertInto(comments, comments.CONTENT, comments.CREATED_AT
                        , comments.POST_ID, comments.USER_OWNER_ID,
                        comments.POST_USER_ID, comments.VOTE_COUNT
                        , comments.REPLY_COUNT
                )
                .values(comment.getContent(), LocalDateTime.now(), comment.getPostId(),
                        comment.getUserOwnerId(), comment.getPostUserId(), 0, 0)
                .returning(comments.ID)
                .fetchOne();


        int id = commentsRecord.getValue(comments.ID);
        return getCommentById(id);
    }
}
