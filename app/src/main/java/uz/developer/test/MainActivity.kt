package uz.developer.test
import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.media.MediaPlayer.create
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.bottom_sheet.*


class MainActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>
    private var totaltime: Int =0
    var playlist: ArrayList<Int>? = null
    public var mediaPlayer: MediaPlayer? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    var item_number: Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        play_btn_layout.setOnClickListener(this)
        forward_bottom.setOnClickListener(this)
        btn_back_layout.setOnClickListener(this)
        btn_next_layout.setOnClickListener(this)
        play_btn.setOnClickListener(this)
        back_last.setOnClickListener(this)
        next_btn.setOnClickListener(this)
        list_item.setOnClickListener(this)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        // music player
        playlist = ArrayList()
        playlist!!.add(R.raw.music)
        playlist!!.add(R.raw.musics)
        playlist!!.add(R.raw.musicc)

            mediaPlayer = create(this, playlist!!.get(2))

        mediaPlayer!!.start()
        seek_bar.progress = 0
        seek_bar.max = mediaPlayer!!.duration
        totaltime = mediaPlayer!!.duration


        val songAdapter =  MySongAdapter()
        songAdapter.renewItems(playlist!!)
        recyclerView.adapter=songAdapter


        // Image for head page
        val imageSlider = findViewById<SliderView>(R.id.imageSlider)
        val imageList: ArrayList<String> = ArrayList()
        imageList.add("https://app.surprizeme.ru/media/store/1186_i1KaYnj_8DuYTzc.jpg")
        imageList.add("https://cdn.getyourguide.com/img/tour/5f4fcda11cfd2.jpeg/148.jpg")
        imageList.add("https://azertag.az/files/galleryphoto/2021/2/162610300964312430.jpg")
        setImageInSlider(imageList, imageSlider)

        // bottomsheet
        sheetBehavior = BottomSheetBehavior.from<LinearLayout>(bottom_sheet)
        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        linerID.visibility = View.GONE
                        linerbottom.visibility = View.VISIBLE
                        linerlist.visibility = View.GONE
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        linerID.visibility = View.GONE
                        linerbottom.visibility = View.VISIBLE
                        linerlist.visibility = View.GONE

                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        mediaPlayer!!.pause()
                        play_btn_layout.setImageResource(R.drawable.ic_pause_white)
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        linerID.visibility = View.VISIBLE
                        linerbottom.visibility = View.GONE
                        linerlist.visibility= View.GONE
                        mediaPlayer!!.pause()
                        play_btn_layout.setImageResource(R.drawable.ic_baseline_play_arrow_24_wh)
                        play_btn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

        seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer!!.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        val handler2 = Handler()
        handler2.postDelayed(object : Runnable{
            override fun run() {
                try {
                    seek_bar.progress = mediaPlayer!!.currentPosition
                    seek_bar2.progress = mediaPlayer!!.currentPosition
                    handler2.postDelayed(this,1000)
                }catch (e:Exception){
                    seek_bar.progress=0
                    seek_bar2.progress = 0
                }
            }
        },0)

//        Thread(Runnable {
//            while (mediaPlayer != null) {
//                try {
//                    var msg = Message()
//                    msg.what = mediaPlayer!!.currentPosition
//                    handler.sendMessage(msg)
//                    Thread.sleep(1000)
//                } catch (e: InterruptedException) {
//
//                }
//            }
//        }).start()

    }

//    @SuppressLint("HandlerLeak")
//    var handler = object :Handler(){
//        override fun handleMessage(msg: Message) {
//            var currentPositions = msg.what
//            seek_bar2.progress = currentPositions
//
//            var txtlasttimee = createTime(currentPositions)
//            txtlasttime.text= txtlasttimee
//
//            var remainingtime = createTime(totaltime - currentPositions)
//            nexttime.text = "-$remainingtime"
//        }
//    }
    private fun setImageInSlider(images: ArrayList<String>, imageSlider: SliderView) {
        val adapter = MySliderImageAdapter()
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
//        imageSlider.isAutoCycle = true
//        imageSlider.startAutoCycle()
    }
//    fun createTime(time: Int): String{
//        var timeLabel =""
//        var min = time/100/60
//        var sec = time/100%60
//
//        timeLabel="$min:"
//        if (sec<10) timeLabel+="0"
//        timeLabel+=sec
//        return timeLabel
//    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.forward_bottom->{
            }
            R.id.play_btn_layout->{
                play_btn_layout.startAnimation(AnimationUtils.loadAnimation(this, R.anim.animation))
                if (!mediaPlayer!!.isPlaying) {
                    mediaPlayer!!.start()
                    seek_bar2.max = mediaPlayer!!.duration
                    val handler = Handler()
                    handler.postDelayed(object : Runnable {
                        override fun run() {
                            try {
                                seek_bar2.progress = mediaPlayer!!.currentPosition
                                handler.postDelayed(this, 1000)
                            } catch (e: Exception) {
                                seek_bar2.progress = 0
                            }
                        }
                    }, 0)

                    play_btn_layout.setImageResource(R.drawable.ic_pause_white)
                } else {
                    mediaPlayer!!.pause()
                    play_btn_layout.setImageResource(R.drawable.ic_baseline_play_arrow_24_wh)
                }

            }
            R.id.btn_back_layout->{
                btn_back_layout.startAnimation(AnimationUtils.loadAnimation(this, R.anim.animation))
                var seekto: Int = mediaPlayer!!.currentPosition - 5000
                if (seekto > mediaPlayer!!.duration)
                    seekto = mediaPlayer!!.duration
                mediaPlayer!!.pause()
                mediaPlayer!!.seekTo(seekto)
                mediaPlayer!!.start()
            }
            R.id.btn_next_layout->{
                btn_next_layout.startAnimation(AnimationUtils.loadAnimation(this, R.anim.animation))
                var seekto:Int = mediaPlayer!!.currentPosition + 5000
                if (seekto<0)
                    seekto=0
                mediaPlayer!!.pause()
                mediaPlayer!!.seekTo(seekto)
                mediaPlayer!!.start()
            }
            R.id.play_btn->{
                play_btn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.animation))
                if (!mediaPlayer!!.isPlaying){
                    mediaPlayer!!.start()
                    seek_bar.max = mediaPlayer!!.duration
                    val handler = Handler()
                    handler.postDelayed(object : Runnable{
                        override fun run() {
                            try {
                                seek_bar.progress = mediaPlayer!!.currentPosition
                                handler.postDelayed(this,1000)
                            }catch (e:Exception){
                                seek_bar.progress=0
                            }
                        }
                    },0)
                    play_btn.setImageResource(R.drawable.ic_pause)
                }
                else{
                    mediaPlayer!!.pause()
                    play_btn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                }
            }
            R.id.back_last->{
                back_last.startAnimation(AnimationUtils.loadAnimation(this, R.anim.animation))
                var seekto: Int = mediaPlayer!!.currentPosition -5000
                if (seekto>mediaPlayer!!.duration)
                    seekto=mediaPlayer!!.duration
                mediaPlayer!!.pause()
                mediaPlayer!!.seekTo(seekto)
                mediaPlayer!!.start()
            }
            R.id.next_btn->{
                next_btn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.animation))
                var seekto:Int = mediaPlayer!!.currentPosition + 5000
                if (seekto<0)
                    seekto=0
                mediaPlayer!!.pause()
                mediaPlayer!!.seekTo(seekto)
                mediaPlayer!!.start()
            }
            R.id.list_item->{
               list_item.startAnimation(AnimationUtils.loadAnimation(this, R.anim.animation))
                linerID.visibility = View.GONE
                linerbottom.visibility = View.GONE
                linerlist.visibility=View.VISIBLE
                mediaPlayer!!.stop()
            }
        }
    }}


