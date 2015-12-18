# LocateMe

        ImageButton fbBut = (ImageButton) findViewById(R.id.facebook_button);
        fbBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello");
                startActivity(Intent.createChooser(shareIntent, "This sharefsda"));
            }
        });



         *Use this to set a URL to share.
         *ShareLinkContent.Builder().setContentUrl(Uri.parse("https://developers.facebook.com")).build();

        content = new ShareLinkContent.Builder().setContentTitle("CURRENT LOCATION").build();

        share = new ShareDialog(this);

        ImageButton fbBut = (ImageButton) findViewById(R.id.facebook_button);
        fbBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share.show(content);
            }
        });
