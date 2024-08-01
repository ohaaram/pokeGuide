package com.pokeguide.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatMessage is a Querydsl query type for ChatMessage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatMessage extends EntityPathBase<ChatMessage> {

    private static final long serialVersionUID = 555219452L;

    public static final QChatMessage chatMessage = new QChatMessage("chatMessage");

    public final DateTimePath<java.time.LocalDateTime> cDate = createDateTime("cDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> chatNo = createNumber("chatNo", Integer.class);

    public final NumberPath<Integer> cmNo = createNumber("cmNo", Integer.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath message = createString("message");

    public final StringPath oName = createString("oName");

    public final StringPath sName = createString("sName");

    public final StringPath uid = createString("uid");

    public QChatMessage(String variable) {
        super(ChatMessage.class, forVariable(variable));
    }

    public QChatMessage(Path<? extends ChatMessage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatMessage(PathMetadata metadata) {
        super(ChatMessage.class, metadata);
    }

}

