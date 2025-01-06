/// 이화경
package com.trace.jachuiplan.board;

import lombok.Getter;

@Getter
enum BoardType {
    INFO('0'),
    GENERAL('1'),
    QNA('2');

    private final Character type;

    BoardType(Character type) {
        this.type = type;
    }
}