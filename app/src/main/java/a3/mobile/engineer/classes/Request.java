package a3.mobile.engineer.classes;

/**
 * Created by asus on 05.01.2016.
 */
public class Request {
    public final static String COL_REQUEST_NUMBER = "RequestNumber";
    public final static String COL_NAME = "Name";
    public final static String COL_DESCRIPTION = "Description";
    public final static String COL_COMPANY = "CompanyIDName";
    public final static String COL_REQUEST_TYPE = "RequestTypeDisplayName";
    public final static String COL_STATUS = "StatusDisplayName";
    public final static String COL_PRIORITY = "Priority";
    public final static String COL_CREATED_AT = "CreatedAt";
    public final static String COL_ASSIGNED_CONTACT = "AssignedContactIDFullName";
    public final static String COL_ASSIGNED_TEAM = "AssignedTeamIDName";
    public final static String COL_USER_CONTACT = "UserContactIDFullName";
    public final static String COL_CREATED_BY = "CreatedByFullName";
    public final static String COL_REQUESTED_BY = "RequestedByDisplayName";
    public final static String COL_SLA_REACTION_TIME = "SLAReactionTime";
    public final static String COL_SLA_RECOVERY_TIME = "SLARecoveryTime";
    public final static String COL_ACTUAL_REACTION_TIME = "ActualReactionTime";
    public final static String COL_ACTUAL_RECOVERY_TIME = "ActualRecoveryTime";
    public final static String COL_SOLUTION_CODE = "SolutionCodeDisplayName";
    public final static String COL_SOLUTION_DESCRIPTION = "SolutionDescription";


    public static String getListColumns() {
        return COL_REQUEST_NUMBER + "," + COL_NAME + "," + COL_PRIORITY;
    }
}
