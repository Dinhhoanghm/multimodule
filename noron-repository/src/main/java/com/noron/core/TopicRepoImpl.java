package com.noron.core;

import com.hm.socialmedia.tables.pojos.Topic;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
public class TopicRepoImpl implements ITopicRepo{
    private final DSLContext dslContext;

    public TopicRepoImpl(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Topic getTopicById(Integer id) {
        return dslContext.selectOne()
                .from(com.hm.socialmedia.tables.Topic.TOPIC)
                .where(com.hm.socialmedia.tables.Topic.TOPIC.ID.eq(id))
                .fetchOneInto(Topic.class);
    }
}
