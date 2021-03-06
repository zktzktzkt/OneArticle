/**
 * Copyright 2017 yidong
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.onlyloveyd.onearticle.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.onlyloveyd.onearticle.R
import cn.onlyloveyd.onearticle.activity.BookmarksActivity
import cn.onlyloveyd.onearticle.database.ArticleInDb
import cn.onlyloveyd.onearticle.database.BookmarksTable
import cn.onlyloveyd.onearticle.database.database
import cn.onlyloveyd.onearticle.extensions.ctx
import kotlinx.android.synthetic.main.article_item.view.*

/**
 * 文 件 名: ArticleRvAdapter
 * 创 建 人: 易冬
 * 创建日期: 2017/08/26
 * 邮   箱: onlyloveyd@gmail.com
 * 博   客: https://onlyloveyd.cn
 * 描   述：主界面
 */
class ArticleRvAdapter(val articleList: List<ArticleInDb>) : RecyclerView.Adapter<ArticleRvAdapter.ArticleViewHolder>() {

    override fun getItemCount(): Int = articleList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bindArticle(articleList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.article_item, parent, false)
        return ArticleViewHolder(view)
    }


    class ArticleViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        fun bindArticle(article: ArticleInDb) {

            itemView.tv_article_author.text = article.author
            itemView.tv_article_title.text = article.title

            itemView.setOnClickListener {
                val intent = Intent()
                val bundle = Bundle()
                bundle.putSerializable("Article", article)
                intent.putExtras(bundle)
                (itemView.context as BookmarksActivity).setResult(Activity.RESULT_OK, intent)
                (itemView.context as BookmarksActivity).finish()
            }
            itemView.iv_delete.setOnClickListener {
                itemView.context.database.use {
                    val whereCaluse = "${BookmarksTable.TITLE} = \"" + article.title + "\"";
                    val rows = delete(BookmarksTable.NAME, whereCaluse, null)
                    if (rows == 1) {
                        (itemView.context as BookmarksActivity).loadArticle();
                    }
                }
            }

        }
    }

}