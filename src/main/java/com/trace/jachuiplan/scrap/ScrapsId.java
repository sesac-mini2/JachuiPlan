package com.trace.jachuiplan.scrap;

import com.trace.jachuiplan.regioncd.Regioncd;
import com.trace.jachuiplan.user.Users;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ScrapsId implements Serializable {
    private Regioncd regioncd;
    private Users users;
}
