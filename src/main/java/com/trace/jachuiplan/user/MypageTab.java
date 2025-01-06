/// 김정은
package com.trace.jachuiplan.user;

import lombok.Getter;

@Getter
public enum MypageTab {
    INFO(0),
    REGION(1),
    POSTS(2),
    LIKES(3);

    private final int type;

    MypageTab(int type){
        this.type = type;
    }
}
