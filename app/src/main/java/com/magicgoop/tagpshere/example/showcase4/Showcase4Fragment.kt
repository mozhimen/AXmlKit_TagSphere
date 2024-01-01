package com.magicgoop.tagpshere.example.showcase4

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.magicgoop.tagpshere.example.R
import com.magicgoop.tagpshere.example.databinding.FragmentShowcase3Binding
import com.magicgoop.tagsphere.OnTagTapListener
import com.magicgoop.tagsphere.item.TagItem
import com.mozhimen.basick.elemk.androidx.fragment.bases.BaseFragmentVB
import com.mozhimen.basick.utilk.android.graphics.bitmapAny2bitmapDrawable
import com.mozhimen.basick.utilk.androidx.lifecycle.runOnMainScope
import com.mozhimen.basick.utilk.kotlin.strUrl2bitmapOfGlide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Showcase4Fragment : BaseFragmentVB<FragmentShowcase3Binding>() {
    companion object {
        fun newInstance(): Showcase4Fragment =
            Showcase4Fragment()

        val imageUrlList = listOf(
            "https://lele-res.lelejoy.com/yx_20230921155041/dz_20230922113412/60Seconds_1701068955388/icon_1701068956507.png?q-sign-algorithm=sha1&q-ak=AKIDXsbAkVmauo3v7AoVr65B6oX9ww1U_O0IUgQGB-WDsUi2bT-adAta-cNoW6F_XRFr&q-sign-time=1703645725;1703646085&q-key-time=1703645724;1703652924&q-header-list=&q-url-param-list=&q-signature=d6a7ee53a0777bbfcbd93174611766e942aaf0a8&x-cos-security-token=mvaonOIiRG73onzUwta94ei3IFgAydoa3af0cfb931074621b0d4813cbf463bf9WPMCkB3_88U0n1LgO-YjYJ6oxfLzJ5vOuOps6PtGpQM3QuNrVQ6s5RtRt1oO4LEZULNOfi1TwF2hOehF10V5VqDq3j6KhH52hs5InBEvQuHTL66jmjJG36PdXLWEISv4af9sODSwKGM1hC94ZSGhcV28W0XbEX8V1x_NA7KbNXZWeLqcnke2BrOMwQU4nMHw247QFQk8gQRvH_QQp6-saazHlksnAl2GV7eBWqTXvpBCId0wx2a8FbxEao-3jpKclYD441QVcUc7Ue8a5lSJMnkiMT7B0GRk8GfFJ3e-IYW6W-TDqLetQpXnzHU5fqw8coVsKJgPkpG0VljKEW9wsasblspr_hi8BbDVSqAC7_O7GphXIGWI_OaZVcUpsf1pxHdOZ9gkAjBua3j2HTqVgIStseRuqvgNiJ4rMufLKjZi-uwH_1t9HDOv-TrC_WZBBJraQe3nMpQIQIkZXTjIqLagAodpt-nstPw3koTbkVnEp6XIRgqStFAB8_iKDvZTgtJF23wAxxSUzKK1hA0T7bsB0KIe3xnfWUgGsXCKvuPZE2k3xfa_egk2hEZKOoZwn0W_lmUfG5j1orS-nlb3JPc5QuRbVNLOBRqs3wen8UT3YbxK7Ct-_3-QkVMBB2T4y0dEPYRC2cixIlYdLUjpSxb0At0wVBWIxO51J3NWu1FHNPzzYJPO_vT5rstMiNFv-0RrDglsNy5DOpAkWwis7fem0QDwLKK1-sc11og0erQm7y2v25_1ZUbjDLfjZnZc-EW0tLo-A9M96OHRmM8smA",
            "https://lele-res.lelejoy.com/yx_20230921155041/dz_20230922113412/DeadTrigger_1701154454927/icon_1701154456484.png?q-sign-algorithm=sha1&q-ak=AKIDXsbAkVmauo3v7AoVr65B6oX9ww1U_O0IUgQGB-WDsUi2bT-adAta-cNoW6F_XRFr&q-sign-time=1703645725;1703646085&q-key-time=1703645724;1703652924&q-header-list=&q-url-param-list=&q-signature=dca5f3f3e2dcf250ceb175bb118fe036e21929c5&x-cos-security-token=mvaonOIiRG73onzUwta94ei3IFgAydoa3af0cfb931074621b0d4813cbf463bf9WPMCkB3_88U0n1LgO-YjYJ6oxfLzJ5vOuOps6PtGpQM3QuNrVQ6s5RtRt1oO4LEZULNOfi1TwF2hOehF10V5VqDq3j6KhH52hs5InBEvQuHTL66jmjJG36PdXLWEISv4af9sODSwKGM1hC94ZSGhcV28W0XbEX8V1x_NA7KbNXZWeLqcnke2BrOMwQU4nMHw247QFQk8gQRvH_QQp6-saazHlksnAl2GV7eBWqTXvpBCId0wx2a8FbxEao-3jpKclYD441QVcUc7Ue8a5lSJMnkiMT7B0GRk8GfFJ3e-IYW6W-TDqLetQpXnzHU5fqw8coVsKJgPkpG0VljKEW9wsasblspr_hi8BbDVSqAC7_O7GphXIGWI_OaZVcUpsf1pxHdOZ9gkAjBua3j2HTqVgIStseRuqvgNiJ4rMufLKjZi-uwH_1t9HDOv-TrC_WZBBJraQe3nMpQIQIkZXTjIqLagAodpt-nstPw3koTbkVnEp6XIRgqStFAB8_iKDvZTgtJF23wAxxSUzKK1hA0T7bsB0KIe3xnfWUgGsXCKvuPZE2k3xfa_egk2hEZKOoZwn0W_lmUfG5j1orS-nlb3JPc5QuRbVNLOBRqs3wen8UT3YbxK7Ct-_3-QkVMBB2T4y0dEPYRC2cixIlYdLUjpSxb0At0wVBWIxO51J3NWu1FHNPzzYJPO_vT5rstMiNFv-0RrDglsNy5DOpAkWwis7fem0QDwLKK1-sc11og0erQm7y2v25_1ZUbjDLfjZnZc-EW0tLo-A9M96OHRmM8smA",
            "https://lele-res.lelejoy.com/yx_20230921155041/mn_20231019174415/AvakinLife_1701157497185/icon_1701157498306.png?q-sign-algorithm=sha1&q-ak=AKIDXsbAkVmauo3v7AoVr65B6oX9ww1U_O0IUgQGB-WDsUi2bT-adAta-cNoW6F_XRFr&q-sign-time=1703645725;1703646085&q-key-time=1703645724;1703652924&q-header-list=&q-url-param-list=&q-signature=8dffeccd240c73bca1ccdac026d7e30fae975366&x-cos-security-token=mvaonOIiRG73onzUwta94ei3IFgAydoa3af0cfb931074621b0d4813cbf463bf9WPMCkB3_88U0n1LgO-YjYJ6oxfLzJ5vOuOps6PtGpQM3QuNrVQ6s5RtRt1oO4LEZULNOfi1TwF2hOehF10V5VqDq3j6KhH52hs5InBEvQuHTL66jmjJG36PdXLWEISv4af9sODSwKGM1hC94ZSGhcV28W0XbEX8V1x_NA7KbNXZWeLqcnke2BrOMwQU4nMHw247QFQk8gQRvH_QQp6-saazHlksnAl2GV7eBWqTXvpBCId0wx2a8FbxEao-3jpKclYD441QVcUc7Ue8a5lSJMnkiMT7B0GRk8GfFJ3e-IYW6W-TDqLetQpXnzHU5fqw8coVsKJgPkpG0VljKEW9wsasblspr_hi8BbDVSqAC7_O7GphXIGWI_OaZVcUpsf1pxHdOZ9gkAjBua3j2HTqVgIStseRuqvgNiJ4rMufLKjZi-uwH_1t9HDOv-TrC_WZBBJraQe3nMpQIQIkZXTjIqLagAodpt-nstPw3koTbkVnEp6XIRgqStFAB8_iKDvZTgtJF23wAxxSUzKK1hA0T7bsB0KIe3xnfWUgGsXCKvuPZE2k3xfa_egk2hEZKOoZwn0W_lmUfG5j1orS-nlb3JPc5QuRbVNLOBRqs3wen8UT3YbxK7Ct-_3-QkVMBB2T4y0dEPYRC2cixIlYdLUjpSxb0At0wVBWIxO51J3NWu1FHNPzzYJPO_vT5rstMiNFv-0RrDglsNy5DOpAkWwis7fem0QDwLKK1-sc11og0erQm7y2v25_1ZUbjDLfjZnZc-EW0tLo-A9M96OHRmM8smA",
        )
    }

    override fun initView(savedInstanceState: Bundle?) {
        initLicenseText()
        runOnMainScope {
            initTagView()
        }
    }

    private suspend fun initTagView() {
        val tags = mutableListOf<BitmapDrawableTagItem>()
        imageUrlList.forEach { url ->
            val res = getBitmapDrawable(url)
            tags.add(BitmapDrawableTagItem(res))
        }
        vb.tagSphereView.addTagList(tags)
        vb.tagSphereView.setRadius(2.75f)
        vb.tagSphereView.setOnTagTapListener(object : OnTagTapListener {
            override fun onTap(tagItem: TagItem) {
                Toast.makeText(requireContext(), "Stay calm and don't get sick", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private suspend fun getBitmapDrawable(url: String): BitmapDrawable =
        withContext(Dispatchers.IO) {
            url.strUrl2bitmapOfGlide(R.drawable.ic_gray_size500, 500, 500).bitmapAny2bitmapDrawable()
        }
    /*ContextCompat.getDrawable(requireContext(), id)*/

    private fun initLicenseText() {
        vb.tvLicense.text = HtmlCompat.fromHtml(
            resources.getString(R.string.icons_made_by_freepik),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        vb.tvLicense.movementMethod = LinkMovementMethod.getInstance()
        vb.tvLicense.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.flaticon.com/authors/freepik"))
            requireActivity().packageManager?.let {
                intent.resolveActivity(it)?.let {
                    requireActivity().startActivity(intent)
                }
            }
        }
    }
}