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
        
        
        //Use to share from any system apps
        String loc = getLatLng();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "http://maps.google.com/?q="+ loc);
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this site!");
        startActivity(Intent.createChooser(intent, "Share"));
