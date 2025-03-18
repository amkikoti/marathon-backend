package com.kikoti.erbmarathon.entity;

import java.time.LocalDateTime;

public abstract class BaseModel {
    protected String createdBy;
    protected String updatedBy;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
}
