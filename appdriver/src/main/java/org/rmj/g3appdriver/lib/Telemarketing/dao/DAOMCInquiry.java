package org.rmj.g3appdriver.lib.Telemarketing.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.lib.Telemarketing.entities.EMCInquiry;

import java.util.Date;
import java.util.List;

@Dao
public interface DAOMCInquiry {
    @Query("SELECT * FROM MC_Inquiry WHERE sTransNox= :sTransNox")
    EMCInquiry GetMCInquiry(String sTransNox);
    @Query("SELECT b.sBrandNme sBrandNme, c.sModelNme sModelNme, d.sColorNme sColorNme, a.cApplType sPurchType, " +
            "h.nSelPrice  nSelPrice, e.nMinDownx nMinDownx, f.nMiscChrg nMiscChrg, f.nRebatesx nRebatesx, f.nEndMrtgg nEndMrtgg " +
            "FROM MC_Inquiry a " +
            "LEFT JOIN MC_Brand b ON (a.sBrandIDx = b.sBrandIDx) " +
            "LEFT JOIN MC_Model c ON (a.sModelIDx = c.sModelIDx) " +
            "LEFT JOIN MC_Model_Color d ON (d.sColorIDx = a.sColorIDx) " +
            "LEFT JOIN Mc_Model_Price e ON (a.sModelIDx = e.sModelIDx) " +
            "LEFT JOIN MC_Category f ON (e.sMCCatIDx = f.sMcCatIDx) " +
            "LEFT JOIN MC_Term_Category g ON (e.sMCCatIDx = g.sMCCatIDx) " +
            "LEFT JOIN MC_Cash_Price h ON (a.sBrandIDx = h.sBrandIDx " +
            "AND a.sModelIDx = h.sModelIDx AND e.sMCCatIDx = h.sMCCatIDx) " +
            "WHERE a.sTransNox= :sTransNox")
    LiveData<ProductInfo> GetMCInquiryDetails(String sTransNox);
    @Query("UPDATE MC_Inquiry SET dFollowUp= :dFollowUp WHERE sTransNox= :sTransNox")
    int UpdateFollowUp(String dFollowUp, String sTransNox);
    @Insert
    Long SaveMCInq(EMCInquiry emcInquiry);
    @Update
    int UpdateMCInq(EMCInquiry emcInquiry);
    @Query("DELETE FROM MC_Inquiry")
    int RemoveInquiries();
    class ProductInfo{
        public String sBrandNme;
        public String sModelNme;
        public String sColorNme;
        public String sPurchType;
        public double nSelPrice;
        public double nMinDownx;
        public double nMiscChrg;
        public double nRebatesx;
        public double nEndMrtgg;
    }
}
