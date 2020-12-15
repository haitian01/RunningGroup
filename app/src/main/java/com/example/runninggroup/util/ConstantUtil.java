package com.example.runninggroup.util;

public class ConstantUtil {




    private ConstantUtil () {}
    //app后端版本（对应不同的后端）
    public final static int VERSION = 1;
//    public final static String URL = "http://192.168.43.222:8080";
    public final static String URL = "http://10.28.170.186:8080";
    //用户表controller
    public final static String ADD_USER = "/user/addUser";
    public final static String UPDATE_USER = "/user/updateUser";
    public final static String GET_USER = "/user/getUser";
    public final static String ADMINISTRATORS = "/user/administrators";
        //退出跑团
    public final static String SIGN_OUT_TEAM = "/user/signOutTeam";

    //活动表controller
    public final static String ADD_ACT = "/act/addAct";
    public final static String UPDATE_ACT = "/act/updateAct";
    public final static String GET_ACT = "/act/getAct";
    public static final String DELETE_ACT = "/act/deleteAct";

    //好友表controller
    public final static String ADD_FRIENDR_RELATION = "/friendRelation/addFriendRelation";
    public final static String UPDATE_FRIENDR_RELATION = "/friendRelation/updateFriendRelation";
    public final static String GET_FRIENDR_RELATION = "/friendRelation/getFriendRelation";

    //好友申请表
    public final static String ADD_FRIENDR_APPLICATION = "/friendApplication/addFriendApplication";
    public final static String UPDATE_FRIENDR_APPLICATION = "/friendApplication/updateFriendApplication";
    public final static String GET_FRIENDR_APPLICATION = "/friendApplication/getFriendApplication";
    public final static String DELETE_FRIENDR_APPLICATION = "/friendApplication/deleteFriendApplication";
    public final static String START_FRIENDR_APPLICATION = "/friendApplication/startFriendApplication";
    public final static String AGREE_TO_REFUSE = "/friendApplication/agreeToRefuse";

    //跑团申请表
    public final static String ADD_TEAM_APPLICATION = "/teamApplication/addTeamApplication";
    public final static String GET_TEAM_APPLICATION = "/teamApplication/getTeamApplication";
    public final static String UPDATE_TEAM_APPLICATION = "/teamApplication/updateTeamApplication";
    public final static String DELETE_TEAM_APPLICATION = "/teamApplication/deleteTeamApplication";

    //朋友圈controller
    public final static String ADD_FRIEND_CIRCLE = "/friendCircle/addFriendCircle";
    public final static String UPDATE_FRIEND_CIRCLE = "/friendCircle/updateFriendCircle";
    public final static String GET_FRIEND_CIRCLE = "/friendCircle/getFriendCircle";
    public final static String GET_FRIEND_CIRCLE_First = "/friendCircle/getFriendCircleFirst";
    public final static String GIVE_ZAN = "/friendCircle/giveZan";
    public static final String DELETE_FRIEND_CIRCLE = "/friendCircle/deleteFriendCircle";

    //评论表
    public final static String GET_COMMENT = "/comment/selectCommentByFriendCircleId";
    public final static String ADD_COMMENT = "/comment/insertComment";
    public final static String DELETE_COMMENT = "/comment/deleteComment";


    //分数表controller
    public final static String ADD_SCORE = "/score/addScore";
    public final static String UPDATE_SCORE = "/score/updateScore";
    public final static String GET_SCORE = "/score/getScore";

    //跑团表controller
    public final static String ADD_TEAM = "/team/addTeam";
    public final static String BUILD_TEAM = "/team/buildTeam";
    public final static String UPDATE_TEAM = "/team/updateTeam";
    public final static String GET_TEAM = "/team/getTeam";
        //解散跑团
    public final static String DELETE_TEAM = "/team/deleteTeam";
    public final static String REMOVE_USER_FROM_TEAM = "/team/removeUserFromTeam";

    //跑团公告表 controller
    public final static String ADD_TEAM_NOTICE= "/teamNotice/addTeamNotice";
    public static final String GET_TEAM_NOTICE = "/teamNotice/getTeamNotice";
    public static final String GET_TEAM_NOTICE_LIMIT = "/teamNotice/getTeamNoticeLimit";

    //文件上传
    public final static String UPLOAD = "/file/upload";
    public final static String GET_IMG = "/file/getImg";


    //界面常量
    public static final int MAIN_INTERFACE = 0;
    public static final int PERSON_SEARCH = 1;
    public static final int MEMBER_SORT = 2;
    public static final int CHANGE_PASSWORD = 3;

    //viewPageNum
    public static final int FRAGMENT_FRIEND_CIRCLE = 3;
    public static final int FRAGMENT_DATA = 0;
    public static final int FRAGMENT_CARD = 1;
    public static final int FRAGMENT_FRIENDS = 2;

    //最大加载次数
    public static final int MAX_LOAD_TIME = 10;



}
