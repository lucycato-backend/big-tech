package org.lucycato.common.model.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.lucycato.common.SelfValidating;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskKey {
    private String transactionUUID;

    private TaskKeyCategory taskKeyCategory;
}
