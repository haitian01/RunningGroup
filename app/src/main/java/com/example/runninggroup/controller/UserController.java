package com.example.runninggroup.controller;

import android.graphics.drawable.Drawable;
import android.net.IpSecManager;
import android.widget.ImageView;

import com.example.runninggroup.cache.Cache;
import com.example.runninggroup.dao.FileDao;
import com.example.runninggroup.dao.UserDao;
import com.example.runninggroup.pojo.User;
import com.example.runninggroup.util.ImgNameUtil;
import com.example.runninggroup.util.StringUtil;

import java.io.File;
import java.util.List;

public class UserController {
    private UserControllerInterface mUserControllerInterface;
    public UserController (UserControllerInterface userControllerInterface) {
        mUserControllerInterface = userControllerInterface;
    }

    //登陆验证
    public void isLoad (String registerNum, String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.setRegisterNum(registerNum);
                user.setPassword(password);
                List<User> users = UserDao.getUser(user);
                if (users == null) mUserControllerInterface.isLoadBack(null, "网络故障");
                else if (users.size() == 0) {
                    mUserControllerInterface.isLoadBack(null, "用户名或密码错误");
                }else {
                    mUserControllerInterface.isLoadBack(users.get(0), "登录成功");
                }
            }
        }).start();
    }
    //查找用户
    public void selectUser (String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.setUsername(msg);
                List<User> users = UserDao.getUser(user);
                if (users == null) mUserControllerInterface.selectUserBack(null);
                else {
                    mUserControllerInterface.selectUserBack(users);
                }
            }
        }).start();
    }
    //查找用户
    public void  selectUserByUser(User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<User> users = UserDao.getUser(user);
                mUserControllerInterface.selectUserByUserBack(users);

            }
        }).start();
    }


    //注册用户
    public void register (String mail, String password, String sex, String username) {
        User user = new User();
        user.setMail(mail);
        user.setPassword(password);
        user.setUsername(username);
        user.setSex(sex.equals("男") ? 1 : 2);
        for (int i = 0; i < 10; i++) {
            String registerNum = StringUtil.getRegisterNum();
            user.setRegisterNum(registerNum);
            if (UserDao.addUser(user)) {
                mUserControllerInterface.registerBack(true, registerNum);
                return;
            }

        }
        mUserControllerInterface.registerBack(false, "请稍后尝试");


    }
    //更换头像
    public void changeHeadImg (File file) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int id = Cache.user.getId();
                String imgName = ImgNameUtil.getUserHeadImgName(id);

                User user = new User();
                user.setId(id);
                user.setHeadImg(imgName);
                boolean res1 = UserDao.updateUser(user);
                boolean res2 = FileDao.upload(file, imgName);
                if (res1 && res2) {
                    Cache.user.setHeadImg(imgName);
                    mUserControllerInterface.changeHeadImgBack(true);
                }else mUserControllerInterface.changeHeadImgBack(false);
            }
        }).start();


    }

    //获得头像
    public void getHeadImg (String imgName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable drawable = FileDao.getImg(imgName);
                mUserControllerInterface.getHeadImg(drawable);
            }
        }).start();
    }

    //根据registerNum拿到drawable
    public void getImgByRegisterNum (String registerNum) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.setRegisterNum(registerNum);
                List<User> userList = UserDao.getUser(user);
                if (userList == null || userList.size() == 0) mUserControllerInterface.getImgByRegisterNumBack(null);
                else {
                    Drawable drawable = FileDao.getImg(ImgNameUtil.getUserHeadImgName(userList.get(0).getId()));
                    mUserControllerInterface.getImgByRegisterNumBack(drawable);
                }
            }
        }).start();
    }

    //修改昵称
    public void changeName (String newName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.setId(Cache.user.getId());
                user.setUsername(newName);
                if (UserDao.updateUser(user)) {
                    Cache.user.setUsername(newName);
                    mUserControllerInterface.changeNameBack(true);
                }else mUserControllerInterface.changeNameBack(false);

            }
        }).start();
    }
    //修改昵称
    public void changeSex () {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.setId(Cache.user.getId());
                user.setSex(Cache.user.getSex() == 1 ? 2 : 1);
                if (UserDao.updateUser(user)) {
                    Cache.user.setSex(Cache.user.getSex() == 1 ? 2 : 1);
                    mUserControllerInterface.changeSexBack(true);
                }else mUserControllerInterface.changeSexBack(false);

            }
        }).start();
    }
    //修改密码
    public void changePwd (String newPwd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User();
                user.setId(Cache.user.getId());
                user.setPassword(newPwd);
                if (UserDao.updateUser(user)) {
                    Cache.user.setPassword(newPwd);
                    mUserControllerInterface.changePwdBack(true);
                }else mUserControllerInterface.changePwdBack(false);

            }
        }).start();
    }



    public interface UserControllerInterface {
        //用户登录查询回调函数
        default void isLoadBack (User user, String msg){}
        default void selectUserBack (List<User> users){}
        default void selectUserByUserBack (List<User> users){}
        //注册用户
        default void registerBack (boolean res, String registerNum){}

        //更换头像
        default void changeHeadImgBack (boolean res){}
        //获得头像
        default void getHeadImg (Drawable drawable) {}
        //修改昵称
        default void changeNameBack (boolean res) {}
        //修改性别
        default void changeSexBack (boolean res) {}
        //修改密码
        default void changePwdBack(boolean res) {};
        default void getImgByRegisterNumBack (Drawable drawable){}
    }
}
