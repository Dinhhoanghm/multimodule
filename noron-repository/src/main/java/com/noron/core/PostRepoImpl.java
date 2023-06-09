package com.noron.core;

import com.hm.socialmedia.tables.pojos.Post;
import com.hm.socialmedia.tables.records.PostRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static org.jooq.impl.DSL.rand;
@Repository
public class PostRepoImpl implements IPostRepo {
    private DSLContext dslContext;

    public PostRepoImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    private com.hm.socialmedia.tables.Post posts = com.hm.socialmedia.tables.Post.POST;

    @Override
    public Post getPostById(Integer id) {
        return dslContext.selectOne()
                .from(posts)
                .where(posts.ID.eq(id))
                .fetchOneInto(Post.class);
    }

    @Override
    public List<Post> getAllPostsByCommon() {
        return dslContext.select()
                .from(posts)
                .orderBy(posts.VOTE_COUNT)
                .fetchInto(Post.class);
    }

    @Override
    public List<Post> getAllPostByDate() {

        return dslContext.select()
                .from(posts)
                .orderBy(posts.CREATED_AT)
                .fetchInto(Post.class);
    }

    @Override
    public List<Post> getPostByTopic(Integer id) {
        return dslContext.select()
                .from(posts)
                .where(posts.TOPIC_RELATED_ID.eq(id))
                .fetchInto(Post.class);

    }

    @Override
    public Post insertPost(Post post) {
        PostRecord postRecord = dslContext.insertInto(posts, posts.CONTENT,posts.TITLE, posts.CREATED_AT
                        , posts.USER_OWNER_ID,posts.TOPIC_RELATED_ID
                       , posts.VOTE_COUNT,posts.COMMENT_COUNT)
                .values(post.getContent(),post.getTitle(), LocalDateTime.now(),
                        post.getUserOwnerId(),post.getTopicRelatedId(), 0, 0)
                .returning(posts.ID)
                .fetchOne();


        int id = postRecord.getValue(posts.ID);
        return getPostById(id);
    }

    @Override
    public Post updatePost(Post post, Integer id) {
        dslContext.update(posts)
                .set(posts.UPDATED_AT, LocalDateTime.now())
                .set(posts.TITLE, post.getTitle())
                .set(posts.CONTENT, post.getContent())
                .set(posts.TOPIC_RELATED_ID, post.getTopicRelatedId())
                .where(posts.ID.eq(id))
                .execute();
        return getPostById(id);
    }

    @Override
    public Post deletePost(Integer id){
        dslContext.update(posts)
                .set(posts.DELETED_AT, LocalDateTime.now())
                .where(posts.ID.eq(id))
                .execute();
        return getPostById(id);
    }

    @Override
    public List<Post> getRandPostByTopic(Integer topicId) {
        return dslContext.select(rand())
                .from(posts)
                .where(posts.TOPIC_RELATED_ID.eq(topicId))
                .limit(10)
                .fetchInto(Post.class);
    }
}
