package org.rmj.g3appdriver.GCircle.Notification.NMM;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UniqueIDGenerator {

    public static String CreateUniqueID(int fnRowCount){
        String lsUniqIDx = "";
        try{
            String lsBranchCd = "MX01";
            String lsCrrYear = new SimpleDateFormat("yy", Locale.getDefault()).format(new Date());
            StringBuilder loBuilder = new StringBuilder(lsBranchCd);
            loBuilder.append(lsCrrYear);

            int lnLocalID = fnRowCount + 1;
            String lsPadNumx = String.format("%05d", lnLocalID);
            loBuilder.append(lsPadNumx);
            lsUniqIDx = loBuilder.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return lsUniqIDx;
    }
}
