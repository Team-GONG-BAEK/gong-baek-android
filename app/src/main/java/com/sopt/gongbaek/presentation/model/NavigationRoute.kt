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


    object GroupRegisterNavGraphRoute {
        const val GROUP_REGISTER_NAV_GRAPH = "group_register_route"
        const val GROUP_CYCLE = "group_cycle"
        const val SELECT_DAY = "select_day"
        const val SELECT_DAY_OF_WEEK = "select_day_of_week"
        const val GROUP_TIME = "group_time"
        const val GROUP_CATEGORY = "group_category"
        const val GROUP_COVER = "group_cover"
        const val GROUP_PLACE_PEOPLE = "group_place_people"
        const val GROUP_INTRODUCTION = "group_introduction"
        const val GROUP_REGISTER = "group_register"
    }
}
