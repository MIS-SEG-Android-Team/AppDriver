package org.rmj.g3appdriver.lib.Telemarketing.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Call_Priorities")
public class EPriorities {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "srcIndex")
    public int srcIndex;
    @ColumnInfo(name = "sSourceCD")
    public String sSourceCD;

    public int getIndex() {
        return srcIndex;
    }
    public void setIndex(int index) {
        this.srcIndex = index;
    }
    public String getsSourceCD() {
        return sSourceCD;
    }
    public void setsSourceCD(String sSourceCD) {
        this.sSourceCD = sSourceCD;
    }
}
