package com.pokeguide.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChatFile is a Querydsl query type for ChatFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChatFile extends EntityPathBase<ChatFile> {

    private static final long serialVersionUID = 1715292071L;

    public static final QChatFile chatFile = new QChatFile("chatFile");

    public final DateTimePath<java.time.LocalDateTime> cDate = createDateTime("cDate", java.time.LocalDateTime.class);

    public final StringPath filename = createString("filename");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath uid = createString("uid");

    public QChatFile(String variable) {
        super(ChatFile.class, forVariable(variable));
    }

    public QChatFile(Path<? extends ChatFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChatFile(PathMetadata metadata) {
        super(ChatFile.class, metadata);
    }

}

