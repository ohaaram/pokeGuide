package com.pokeguide.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatUser is a Querydsl query type for ChatUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatUser extends EntityPathBase<ChatUser> {

    private static final long serialVersionUID = 1715748342L;

    public static final QChatUser chatUser = new QChatUser("chatUser");

    public final NumberPath<Integer> chatNo = createNumber("chatNo", Integer.class);

    public final NumberPath<Integer> cuNo = createNumber("cuNo", Integer.class);

    public final StringPath uid = createString("uid");

    public QChatUser(String variable) {
        super(ChatUser.class, forVariable(variable));
    }

    public QChatUser(Path<? extends ChatUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatUser(PathMetadata metadata) {
        super(ChatUser.class, metadata);
    }

}

