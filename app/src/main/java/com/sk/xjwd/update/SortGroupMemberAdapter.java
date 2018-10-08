package com.sk.xjwd.update;

/**
 * 通讯录 Adapter
 */

/*
public class SortGroupMemberAdapter extends BaseAdapter implements SectionIndexer {
    private  String companyType;
    private List<GroupMemberBean> list = null;
    private Context mContext;

    public SortGroupMemberAdapter(Context mContext, List<GroupMemberBean> list, String companyType) {
        this.mContext = mContext;
        this.list = list;
        this.companyType=companyType;
    }


    public void updateListView(List<GroupMemberBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        ViewHolder viewHolder = null;
        final GroupMemberBean mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.row_contact, null);
            AutoUtils.auto(view);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            viewHolder.bbb = (TextView) view.findViewById(R.id.bbb);
            viewHolder.ccc = (TextView) view.findViewById(R.id.ccc);
            viewHolder.ivCall = (ImageView) view.findViewById(R.id.iv_call);
            viewHolder.ivMsg = (ImageView) view.findViewById(R.id.iv_msg);
            viewHolder.rvPic = (RoundView) view.findViewById(R.id.rv_pic);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        int section = getSectionForPosition(position);
        final ContactsBean.RowsBean bean = list.get(position).getBean();

        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(mContent.getSortLetters());

        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }

        viewHolder.tvTitle.setText(this.list.get(position).getBean().getName());
        if (TextUtils.isEmpty(bean.getDepartmentName()))
            viewHolder.bbb.setText("未分配部门");
        else
            viewHolder.bbb.setText(bean.getDepartmentName());
        if (TextUtils.isEmpty(bean.getGroupName()))
            viewHolder.ccc.setText("/未分配职能");
        else
            viewHolder.ccc.setText("/" + bean.getGroupName());
        if ("1".equals(companyType)) {
            Glide.with(mContext).load(Api.OWNER_FILE_URL+bean.getAvatar()).error(R.mipmap.game_picure).into(viewHolder.rvPic);
        } else if ("2".equals(companyType)) {
            Glide.with(mContext).load(Api.SERVICE_FILE_URL+bean.getAvatar()).error(R.mipmap.game_picure).into(viewHolder.rvPic);
        }
        viewHolder.ivMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 聊天 TODO
                Intent intent = new Intent(mContext, ChatActivity.class);
                intent.putExtra(Constant.EXTRA_USER_ID, bean.getMobilePhone());
                mContext.startActivity(intent);
            }
        });
        viewHolder.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+bean.getMobilePhone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        return view;

    }

    final static class ViewHolder {
        TextView tvLetter;
        TextView bbb;
        TextView ccc;
        TextView tvTitle;
        RoundView rvPic;
        ImageView ivCall;
        ImageView ivMsg;

    }


    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }


    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }
*/
