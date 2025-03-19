package com.sopt.gongbaek.presentation.model

object NavigationRoute {
    const val SPLASH = "splash"
    const val ONBOARDING = "onboarding"
    const val GROUP_DETAIL = "group_detail/{groupId}/{groupCycle}"
    const val GROUP_ROOM = "group_room/{groupId}/{groupCycle}"

    object MainBottomNavBarTabRoute {
        const val GROUP_LIST_TAB = "group_list_route"
        const val HOME_TAB = "home_route"

//        const val TIMETABLE_TAB = "timetable_route"
        const val MY_PAGE_TAB = "my_page_route"
    }


}
