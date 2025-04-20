package com.sopt.gongbaek.presentation.ui.home.component.section

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sopt.gongbaek.R
import com.sopt.gongbaek.domain.model.RecommendGroupInfo
import com.sopt.gongbaek.presentation.type.GroupInfoChipType
import com.sopt.gongbaek.presentation.type.ImageSelectorType
import com.sopt.gongbaek.presentation.ui.component.chip.GroupInfoChip
import com.sopt.gongbaek.presentation.ui.component.section.GroupPlaceDescription
import com.sopt.gongbaek.presentation.ui.component.section.GroupTimeDescription
import com.sopt.gongbaek.presentation.util.extension.clickableWithoutRipple
import com.sopt.gongbaek.presentation.util.homeOnceGroupFormatSchedule
import com.sopt.gongbaek.ui.theme.GongBaekTheme

@Composable
fun WeekRecommendSection(
    userNickname: String,
    weekRecommendGroupInfo: List<RecommendGroupInfo>,
    onClickWeekRecommendItem: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.home_week_recommend_section_title),
            color = GongBaekTheme.colors.gray10,
            style = GongBaekTheme.typography.title2.b18,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = buildAnnotatedString {
                val fullText = stringResource(R.string.home_week_recommend_section_subtitle, userNickname)
                append(fullText)

                val highlightText = stringResource(R.string.home_week_recommend_section_highlight)
                val start = fullText.indexOf(highlightText)
                val end = start + highlightText.length

                if (start != -1) {
                    addStyle(
                        style = SpanStyle(
                            color = GongBaekTheme.colors.mainOrange
                        ),
                        start = start,
                        end = end
                    )
                }
            },
            color = GongBaekTheme.colors.gray06,
            style = GongBaekTheme.typography.body2.m14,
            modifier = Modifier.padding(start = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (weekRecommendGroupInfo.isEmpty()) {
            EmptyRecommendSection(
                image = R.drawable.img_my_fill_active_empty,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = weekRecommendGroupInfo) { weekRecommendGroupInfo ->
                WeekRecommendItem(
                    weekRecommendGroupInfo = weekRecommendGroupInfo,
                    onClickWeekRecommendItem = { groupId, groupType ->
                        onClickWeekRecommendItem(groupId, groupType)
                    }
                )
            }
        }
    }
}

@Composable
private fun WeekRecommendItem(
    weekRecommendGroupInfo: RecommendGroupInfo,
    onClickWeekRecommendItem: (Int, String) -> Unit
) {
    Column(
        modifier = Modifier
            .clickableWithoutRipple {
                onClickWeekRecommendItem(
                    weekRecommendGroupInfo.groupId,
                    weekRecommendGroupInfo.groupType
                )
            }
            .width((LocalConfiguration.current.screenWidthDp * 0.32f).dp)
    ) {
        val screenWidth = LocalConfiguration.current.screenWidthDp

        Box {
            Image(
                painter = painterResource(ImageSelectorType.getCoverImage(weekRecommendGroupInfo.category, weekRecommendGroupInfo.coverImg)),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(4.dp))
                    .width((screenWidth * 0.32f).dp)
                    .aspectRatio(116f / 108f)
            )
            GroupInfoChip(
                groupInfoChipType = GroupInfoChipType.getHomeChipTypeFromCategory(weekRecommendGroupInfo.category),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 8.dp, bottom = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = weekRecommendGroupInfo.groupTitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = GongBaekTheme.colors.gray10,
            style = GongBaekTheme.typography.body2.sb14,
            modifier = Modifier.width((screenWidth * 0.32f).dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        GroupTimeDescription(
            description = homeOnceGroupFormatSchedule(
                weekRecommendGroupInfo.weekDay,
                weekRecommendGroupInfo.startTime,
                weekRecommendGroupInfo.endTime
            ),
            textColor = GongBaekTheme.colors.gray06,
            textStyle = GongBaekTheme.typography.caption2.m12
        )

        Spacer(modifier = Modifier.height(2.dp))

        GroupPlaceDescription(
            description = weekRecommendGroupInfo.location,
            textColor = GongBaekTheme.colors.gray06,
            textStyle = GongBaekTheme.typography.caption2.m12
        )
    }
}

@Preview
@Composable
private fun PreviewWeekSection() {
    WeekRecommendSection(
        userNickname = "김대현",
        onClickWeekRecommendItem = { _, _ -> },
        weekRecommendGroupInfo = listOf(
            RecommendGroupInfo(
                groupTitle = "스터디 모임",
                nickname = "김대현",
                weekDate = "2021-09-20",
                profileImg = 5,
                category = "DINING",
                coverImg = 3
            ),
            RecommendGroupInfo(
                groupTitle = "운동 모임",
                nickname = "김대현1",
                weekDate = "2021-09-20",
                profileImg = 1
            ),
            RecommendGroupInfo(
                groupTitle = "스터디 모임",
                nickname = "김대현2",
                weekDate = "2021-09-20",
                profileImg = 3
            ),
            RecommendGroupInfo(
                groupTitle = "운동 모임",
                nickname = "김대현3",
                weekDate = "2021-09-20"
            )
        )
    )
}
