package com.acb.bakewellgps.ui.Activities.DashboardPage;

import com.acb.bakewellgps.modell.RoutesData;

import java.util.List;

public interface IDashboardLogic {
    interface logic{
        void callRoutesApi(int EmployeeId);
    }
    interface view{
       void routeListApiCompleted(boolean status, List<RoutesData> routeList,String errorMessage);
    }
}
