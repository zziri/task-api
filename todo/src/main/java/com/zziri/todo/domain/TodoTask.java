package com.zziri.todo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class TodoTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @JsonIgnore
    private long ownerId;

    @Column(length = 200)
    private String title;

    @Column(length = 200)
    private String memo;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public void patch(TodoTask input) {
        if (input.getTitle() != null)
            title = input.getTitle();
        if (input.getMemo() != null)
            memo = input.getMemo();
    }
}
