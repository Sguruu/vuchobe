package ru.vuchobe.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.vuchobe.R;
import ru.vuchobe.model.EventValue;
import ru.vuchobe.model.ResponsePageBody;
import ru.vuchobe.service.NetworkManager;
import ru.vuchobe.util.NetworkUtils;
import ru.vuchobe.util.loaders.ByteArraySaveObjectResult;
import ru.vuchobe.util.loaders.CallbackResult;
import ru.vuchobe.util.loaders.Loader;
import ru.vuchobe.util.loaders.ObjectLoader;
import ru.vuchobe.util.loaders.PageLoader;
import ru.vuchobe.util.loaders.PageSaveResult;
import ru.vuchobe.util.threadUtil.ThreadFragment;
import ru.vuchobe.util.threadUtil.ThreadLooperLocal;
import ru.vuchobe.util.threadUtil.ThreadService;
import ru.vuchobe.util.threadUtil.ThreadTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListFragment extends ThreadFragment {

    private CoordinatorLayout main;
    private Toolbar toolbar;
    private RecyclerView eventListRecyclerView;

    public EventListFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EventListFragment.
     */
    public static EventListFragment newInstance() {
        EventListFragment fragment = new EventListFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        initUI();

        if(eventListRecyclerView.getAdapter() == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
            eventListRecyclerView.setLayoutManager(linearLayoutManager);

            RecyclerView.Adapter adapter = getAdapterEventList(this);
            eventListRecyclerView.setAdapter(adapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    /**
     * find all uses UI elements
     * Поиск всех UI элементов
     */
    private void initUI() {
        main = getView().findViewById(R.id.mainId);                                                 //Container all next UI elements (Контейнер всех следующих UI)
        toolbar = getView().findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        eventListRecyclerView = getView().findViewById(R.id.event_list_recycler_id);
    }

    private static RecyclerView.Adapter getAdapterEventList(EventListFragment fragment) {
        try {
            ThreadLooperLocal local = fragment.getLooperLocal();

            ObjectLoader<Void, InputStream> imageLoader = new ObjectLoader<Void, InputStream>(
                    local,
                    null,
                    600000
            ) {
                @Override
                protected void load(Object id, Params params, @NonNull CallbackResult<Void, InputStream> callback) {
                    if(id == null) {
                        return;
                    }
                    NetworkManager.objectOutput(
                            String.format("/image/%s", id.toString()),
                            NetworkManager.Method.GET,
                            null,
                            null,
                            null,
                            (HttpURLConnection result, NetworkManager.NetworkException error) -> {
                                if (result != null) {
                                    List<InputStream> inputStreams = null;
                                    try {
                                        inputStreams = Collections.singletonList(
                                                new NetworkUtils.ProgressInputStream(
                                                        result.getInputStream(),
                                                        result.getContentLength()
                                                )
                                        );
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    this.callbackResult(params, null, inputStreams);
                                } else {
                                    this.callbackResult(params, null, null);
                                }
                            }
                    );
                }
            };


            ByteArraySaveObjectResult<Void, Bitmap> imageSaveResult = new ByteArraySaveObjectResult<Void, Bitmap>(
                    local,
                    EventValue.class.getMethod("getImage"),
                    EventValue.class.getMethod("setImageResource", Bitmap.class),
                    imageLoader,
                    null,
                    600000,//load imageLoader
                    20
            ){
                @Override
                public Bitmap convert(byte[] bytes) {
                    if(bytes != null){
                        Bitmap result = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        return result;
                    }
                    return null;
                }

                @Override
                public boolean isEquals(Bitmap a, Bitmap b) {
                    return (a == b) || (a != null && a.sameAs(b));
                }
            };

            PageLoader<ResponsePageBody<EventValue>, EventValue> pageLoader = new PageLoader<ResponsePageBody<EventValue>, EventValue>(
                    local,
                    10,
                    null,
                    60000
            ) {
                @Override
                protected void load(long page, long size, Params params, @NonNull CallbackResult<ResponsePageBody<EventValue>, EventValue> callback) {
                    NetworkManager.objectObject(
                            String.format("/activity?page=%d&size=%d", page, size),
                            NetworkManager.Method.GET,
                            null,
                            null,
                            null,
                            EventValue.PAGE_BODY_CLASS,
                            (ResponsePageBody<EventValue> result, NetworkManager.NetworkException error) -> {
                                if (result != null) {
                                    List<EventValue> values = result.getContent();
                                    result.setContent(Collections.EMPTY_LIST);
                                    this.callbackResult(params, result, values);
                                } else {
                                    this.callbackResult(params, null, null);
                                }
                            }
                    );
                }
            };

            EventAdapter adapter = new EventAdapter();

            final PageSaveResult<ResponsePageBody<EventValue>, EventValue> pageSaveResult = new PageSaveResult<ResponsePageBody<EventValue>, EventValue>(
                    local,
                    10,
                    null,
                    null,
                    pageLoader,
                    Arrays.asList(imageSaveResult),
                    60000,
                    64
            ) {
                @Nullable
                @Override
                public long size() {
                    ResponsePageBody responsePageBody = this.getValue();
                    return (responsePageBody != null) ? responsePageBody.getTotalElements() : 0;
                }

                private volatile int countUpdateRollback = 0;
                private volatile long updateTime = 0;
                private int uniqueNum = ThreadTask.getUniqueNumGen();

                @Override
                public boolean updateDataUI(@Nullable Loader loader, @Nullable Params params) {
                    if(!super.updateDataUI(loader, params)) return false;
                    long timeNow = System.currentTimeMillis();
                    if(updateTime > timeNow){
                        countUpdateRollback ++;
                        local.asyncMain(
                                ThreadService.Unique.NEW,
                                uniqueNum,
                                (int) (updateTime - timeNow + 100),
                                (ThreadTask task) -> updateDataUI(this, null)
                        );
                        return true;
                    }
                    if(params != null && countUpdateRollback == 0) {
                        PageParams pageParams = this.assertPageParams(params);
                        adapter.notifyItemRangeChanged((int) (pageParams.getPage() * pageParams.getSize()), (int) (pageParams.getSize()));
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                    countUpdateRollback = 0;
                    updateTime = timeNow + 10000;
                    return true;
                }
            };

            adapter.setPageSaveResult(pageSaveResult);
            pageSaveResult.start();

            return adapter;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{

        private PageSaveResult<ResponsePageBody<EventValue>, EventValue> pageSaveResult;

        public EventAdapter() {
        }

        public void setPageSaveResult(PageSaveResult<ResponsePageBody<EventValue>, EventValue> pageSaveResult){
            if(pageSaveResult != null) this.pageSaveResult = pageSaveResult;
        }

        @NonNull
        @Override
        public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_element_list, parent, false);
            view.getLayoutParams().height = (int)(parent.getWidth() * 0.7);
            return new EventViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
            EventValue value = (pageSaveResult != null)? pageSaveResult.getValue(position) : null;
            //setDefaultValue
            if(value == null){
                holder.eventImageView.setTag(null);
                holder.eventImageView.setImageResource(R.drawable.ic_event_element_def);
                holder.eventTitleTextView.setText(R.string.Loading);
                holder.eventDescriptionTextView.setText(R.string.Loading);
                return;
            }

            holder.eventTitleTextView.setText(value.getName());
            holder.eventDescriptionTextView.setText(value.getShortDescription());

            Long id = value.getImage();
            Bitmap image = value.getImageResource();
            if(!Objects.equals(id, holder.eventImageView.getTag())){
                if (image != null) {
                    holder.eventImageView.setTag(id);
                    holder.eventImageView.setImageBitmap(image);
                }else{
                    holder.eventImageView.setTag(null);
                    holder.eventImageView.setImageResource(R.drawable.ic_event_element_def);
                }
            }
        }

        @Override
        public int getItemCount() {
            return (pageSaveResult != null) ? (int)pageSaveResult.size() : 0;
        }

        public class EventViewHolder extends RecyclerView.ViewHolder{
            public ImageView eventImageView;
            public TextView eventTitleTextView;
            public TextView eventDescriptionTextView;

            private Context context;

            public EventViewHolder(@NonNull View itemView) {
                super(itemView);
                this.context = itemView.getContext();

                eventImageView = itemView.findViewById(R.id.eventImageViewId);
                eventTitleTextView = itemView.findViewById(R.id.eventTitleTextViewId);
                eventDescriptionTextView = itemView.findViewById(R.id.eventDescriptionTextViewId);

                eventImageView.setClipToOutline(true);
            }
        }
    }
}
