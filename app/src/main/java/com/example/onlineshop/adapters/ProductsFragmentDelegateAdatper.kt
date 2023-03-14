package com.example.onlineshop.adapters

import android.view.View
import android.view.animation.AnimationUtils
import com.example.onlineshop.R
import com.example.onlineshop.domain.models.AdapterListItem
import com.example.onlineshop.domain.models.*
import com.example.onlineshop.databinding.BrandItemBinding
import com.example.onlineshop.databinding.CategoryItemBinding
import com.example.onlineshop.databinding.FlashSaleItemBinding
import com.example.onlineshop.databinding.ItemsHorizontalListBinding
import com.example.onlineshop.databinding.LatestItemBinding
import com.example.onlineshop.databinding.LoadingFlashSaleItemBinding
import com.example.onlineshop.databinding.LoadingLatestItemBinding
import com.example.onlineshop.utils.loadImage
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

object ProductsFragmentDelegates {

    val loadingItemHorizontalListDelegate =
        adapterDelegateViewBinding<AdapterList, AdapterList, ItemsHorizontalListBinding>(
            { inflate, container ->
                ItemsHorizontalListBinding.inflate(inflate, container, false)
                    .apply {
                        rcView.adapter = ListDelegationAdapter(
                            categoriesItemsDelegate,
                            loadingLatestItemDelegate,
                            loadingFlashSaleItemDelegate,
                            brandsItemsDelegate
                        )
                    }
            }
        ) {
            bind {
                if (item.title == "") {
                    binding.viewAllBtn.visibility = View.GONE
                    binding.title.visibility = View.GONE
                }
                else binding.title.text = item.title
                (binding.rcView.adapter as ListDelegationAdapter<List<AdapterListItem>>).apply {
                    items = item.list
                    notifyDataSetChanged()
                }
            }
        }

    fun itemHorizontalListDelegate(lam: () -> Unit) =
        adapterDelegateViewBinding<AdapterList, AdapterList, ItemsHorizontalListBinding>(
            { inflate, container ->
                ItemsHorizontalListBinding.inflate(inflate, container, false)
                    .apply {
                        rcView.adapter = ListDelegationAdapter(
                            categoriesItemsDelegate,
                            latestItemsDelegate,
                            flashSaleItemDelegate(lam),
                            brandsItemsDelegate
                        )
                    }
            }
        ) {
            bind {
                if (item.title == "") {
                    binding.viewAllBtn.visibility = View.GONE
                    binding.title.visibility = View.GONE
                }
                else binding.title.text = item.title
                (binding.rcView.adapter as ListDelegationAdapter<List<AdapterListItem>>).apply {
                    items = item.list
                    notifyDataSetChanged()
                }
            }
        }

    private val latestItemsDelegate =
        adapterDelegateViewBinding<LatestItem, AdapterListItem, LatestItemBinding>(
            { inflater, container -> LatestItemBinding.inflate(inflater, container, false) }
        ) {
            bind {
                binding.apply {
                    loadImage(bgImage, item.image_url)
                    itemTitle.text = item.name
                    price.text =
                        context.resources.getString(R.string.price_format, item.price.toString())
                    categoryTag.text = item.category
                }
            }
        }

    private fun flashSaleItemDelegate(lam: () -> Unit) =
        adapterDelegateViewBinding<FlashSaleItem, AdapterListItem, FlashSaleItemBinding>(
            { inflater, container -> FlashSaleItemBinding.inflate(inflater, container, false) },
        ) {
            bind {
                binding.apply {
                    bgImage.setOnClickListener {
                        lam()
                    }
                    loadImage(bgImage, item.image_url)
                    discountTitle.text = context.resources.getString(R.string.discount_format, item.discount.toString())
                    itemTitle.text = item.name
                    price.text =
                        context.resources.getString(R.string.price_format, item.price.toString())
                    categoryTag.text = item.category
                }
            }
        }

    private val loadingFlashSaleItemDelegate =
        adapterDelegateViewBinding<FlashSaleItem, AdapterListItem, LoadingFlashSaleItemBinding>(
            { inflater, container -> LoadingFlashSaleItemBinding.inflate(inflater, container, false) }
        ) {
            bind {
                val anim = AnimationUtils.loadAnimation(context, R.anim.loading_card_anim)
                binding.bgImage.animation = anim
            }
        }

    private val loadingLatestItemDelegate =
        adapterDelegateViewBinding<LatestItem, AdapterListItem, LoadingLatestItemBinding>(
            { inflater, container -> LoadingLatestItemBinding.inflate(inflater, container, false) }
        ) {
            bind {
                val anim = AnimationUtils.loadAnimation(context, R.anim.loading_card_anim)
                binding.bgImage.animation = anim
            }
        }

    private val brandsItemsDelegate =
        adapterDelegateViewBinding<BrandItem, AdapterListItem, BrandItemBinding>(
            { inflater, container -> BrandItemBinding.inflate(inflater, container, false) }
        ) {
            bind {
                binding.brandTitle.text = item.name
                loadImage(binding.bgImage, item.image_url)
            }
        }

    private val categoriesItemsDelegate =
        adapterDelegateViewBinding<CategoryItem, AdapterListItem, CategoryItemBinding>(
            { inflater, container -> CategoryItemBinding.inflate(inflater, container, false) }
        ) {
            bind {
                binding.categoryItemTitle.text = item.title
                binding.categoryItemIcon.setImageResource(item.resourceId)
            }
        }


}