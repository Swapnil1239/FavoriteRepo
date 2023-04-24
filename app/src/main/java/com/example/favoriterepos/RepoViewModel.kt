package com.example.favoriterepos
import android.content.Context
import android.util.Log.*
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.*
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class RepoViewModel {

    var repoList: MutableLiveData<MutableList<RepoItem>> = MutableLiveData(mutableListOf())

    internal fun loadList(context:Context) {
        val file = File(context.filesDir, FILE_NAME)

        if (file.exists()) {
            // file exists
            val fis = context.openFileInput(FILE_NAME)
            val ois = ObjectInputStream(fis)
            if (fis.available() > 0) {
                val savedList = ois.readObject() as List<RepoItem>
                if (savedList.isNotEmpty()) {
                    savedList.forEach {
                        repoList.value?.add(it)
                    }
                }
            }
        } else {
            // file does not exist
        }


    }
    internal fun addRepo(context: Context, owner: String, repoName: String) {
        val repo = Repository(owner, repoName)
        var data: RepoItem?
        CoroutineScope(Dispatchers.IO).launch {
            data = repo.getReadableRepoData()
            withContext(Dispatchers.Main) {
                if (data != null) {
                    val fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
                    val oos = ObjectOutputStream(fos)
                    val listToSave = mutableListOf<RepoItem>()
                    repoList.value?.forEach {
                        listToSave.add(it)
                    }
                    oos.writeObject(listToSave)
                    oos.close()
                    fos.close()
                    repoList.value?.add(data!!)
                }
            }
        }
        Executors.newSingleThreadScheduledExecutor().schedule({
            d("zzzz","repoList: ${repoList.value?.size}")
        },5, TimeUnit.SECONDS)
    }

    inner class Repository(private val owner: String,  private val repoName: String): Serializable {

        private fun getRepository(owner: String, repoName: String): JSONObject? {
            val client = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            val request = Request.Builder()
                .url("https://api.github.com/repos/$owner/$repoName")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful)  e("Unexpected code","response: $response")

                val json = JSONObject(response.body!!.string())
                return json
            }
        }

        fun getReadableRepoData(): RepoItem? {
            val jsonData = getRepository(owner, repoName)
            var repoItem: RepoItem ? = null
            if (jsonData != null) {
                repoItem?.mRepoName =
                    if (jsonData.has("name")) {
                        jsonData.getString("name")
                    } else {
                        ""
                    }
                repoItem?.mRepoDescription =
                    if (jsonData.has("description")) {
                        jsonData.getString("description")
                    } else {
                        "Not available"
                    }
                repoItem?.mRepoLink =
                    if (jsonData.has("homepage")) {
                        jsonData.getString("homepage")
                    } else {
                        "Not available"
                    }
            }
            d("zzzz","json data: $jsonData")
            return repoItem
        }
    }

    companion object{
        private const val FILE_NAME = "local_storage.ser"
    }
}