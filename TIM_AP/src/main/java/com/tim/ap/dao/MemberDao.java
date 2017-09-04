package com.tim.ap.dao;

import java.util.ArrayList;

import com.tim.ap.entity.MemberEntity;

public interface MemberDao {
    public ArrayList<MemberEntity> getMemberList();
    //public void insertMember(MemberEntity member);
    //public void updateMember(MemberEntity member);
    //public void deleteMember(MemberEntity member);
}
