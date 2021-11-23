package com.acb.bakewellgps.ui.Activities.DashboardPage;

import com.acb.bakewellgps.modell.RoutesData;

import java.util.List;

public interface IDashboardLogic {
    interface logic{
        void callRoutesApi(int EmployeeId);

        void callLogoutApi(String username);
    }
    interface view{
       void routeListApiCompleted(boolean status, List<RoutesData> routeList,String errorMessage);
        void logoutCallback(boolean status,String errorMessage);
    }
}
