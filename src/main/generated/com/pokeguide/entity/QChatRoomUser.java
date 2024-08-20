package com.pokeguide.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatRoomUser is a Querydsl query type for ChatRoomUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatRoomUser extends EntityPathBase<ChatRoomUser> {

    private static final long serialVersionUID = 320749553L;

    public static final QChatRoomUser chatRoomUser = new QChatRoomUser("chatRoomUser");

    public final NumberPath<Integer> chatNo = createNumber("chatNo", Integer.class);

    public final StringPath uid = createString("uid");

    public QChatRoomUser(String variable) {
        super(ChatRoomUser.class, forVariable(variable));
    }

    public QChatRoomUser(Path<? extends ChatRoomUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatRoomUser(PathMetadata metadata) {
        super(ChatRoomUser.class, metadata);
    }

}

