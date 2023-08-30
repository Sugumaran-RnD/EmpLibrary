package com.nibav.commons.samples.activities

import android.os.Bundle
import com.nibav.commons.activities.BaseSimpleActivity
import com.nibav.commons.dialogs.BottomSheetChooserDialog
import com.nibav.commons.extensions.appLaunched
import com.nibav.commons.extensions.toast
import com.nibav.commons.extensions.viewBinding
import com.nibav.commons.helpers.LICENSE_AUTOFITTEXTVIEW
import com.nibav.commons.models.FAQItem
import com.nibav.commons.models.SimpleListItem
import com.nibav.commons.samples.BuildConfig
import com.nibav.commons.samples.R
import com.nibav.commons.samples.databinding.ActivityMainBinding

class MainActivity : BaseSimpleActivity() {
    override fun getAppLauncherName() = getString(R.string.smtco_app_name)

    override fun getAppIconIDs(): ArrayList<Int> {
        val ids = ArrayList<Int>()
        ids.add(R.mipmap.commons_launcher)
        return ids
    }

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        appLaunched(BuildConfig.APPLICATION_ID)

        updateMaterialActivityViews(binding.mainCoordinator, binding.mainHolder, useTransparentNavigation = true, useTopSearchMenu = false)
        setupMaterialScrollListener(binding.mainNestedScrollview, binding.mainToolbar)

        binding.mainColorCustomization.setOnClickListener {
            startCustomizationActivity()
        }
        binding.bottomSheetChooser.setOnClickListener {
            launchAbout()
        }
    }

    private fun launchAbout() {
        val licenses = LICENSE_AUTOFITTEXTVIEW

        val faqItems = arrayListOf(
            FAQItem(com.nibav.commons.R.string.faq_1_title_commons, com.nibav.commons.R.string.faq_1_text_commons),
            FAQItem(com.nibav.commons.R.string.faq_1_title_commons, com.nibav.commons.R.string.faq_1_text_commons),
            FAQItem(com.nibav.commons.R.string.faq_4_title_commons, com.nibav.commons.R.string.faq_4_text_commons)
        )

        if (!resources.getBoolean(com.nibav.commons.R.bool.hide_google_relations)) {
            faqItems.add(FAQItem(com.nibav.commons.R.string.faq_2_title_commons, com.nibav.commons.R.string.faq_2_text_commons))
            faqItems.add(FAQItem(com.nibav.commons.R.string.faq_6_title_commons, com.nibav.commons.R.string.faq_6_text_commons))
        }

        startAboutActivity(R.string.smtco_app_name, licenses, BuildConfig.VERSION_NAME, faqItems, true)
    }

    private fun launchBottomSheetDemo() {
        BottomSheetChooserDialog.createChooser(
            fragmentManager = supportFragmentManager,
            title = com.nibav.commons.R.string.please_select_destination,
            items = arrayOf(
                SimpleListItem(1, com.nibav.commons.R.string.record_video, com.nibav.commons.R.drawable.ic_camera_vector),
                SimpleListItem(
                    2,
                    com.nibav.commons.R.string.record_audio,
                    com.nibav.commons.R.drawable.ic_microphone_vector,
                    selected = true
                ),
                SimpleListItem(4, com.nibav.commons.R.string.choose_contact, com.nibav.commons.R.drawable.ic_add_person_vector)
            )
        ) {
            toast("Clicked ${it.id}")
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar(binding.mainToolbar)
    }
}
