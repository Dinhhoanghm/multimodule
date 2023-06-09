package com.noron.core;

import com.hm.socialmedia.tables.pojos.Topic;
import org.springframework.stereotype.Component;


public interface ITopicRepo {
    public Topic getTopicById(Integer id);
}
