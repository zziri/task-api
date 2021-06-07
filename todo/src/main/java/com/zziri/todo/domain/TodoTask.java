package com.zziri.todo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TodoTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @JsonIgnore
    private Long ownerId;

    @Builder.Default
    @Column(length = 200)
    private String title = "";

    @Builder.Default
    @Column(length = 200)
    private String memo = "";

    @Builder.Default
    @Column
    private Boolean completed = false;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @CreatedDate
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @JsonIgnore
    public void patch(TodoTask input) {
        if (input.getTitle() != null)
            title = input.getTitle();
        if (input.getMemo() != null)
            memo = input.getMemo();
    }

    @JsonIgnore
    public boolean contentEquals(TodoTask task) {
        return title.equals(task.getTitle())
                && memo.equals(task.getMemo())
                && completed.equals(task.getCompleted());
    }
}
