$(function() {
    // FESSサーバのJSON APIのエンドポイント
    var FESS_JSON_ENDPOINT = 'http://192.168.1.5:10084/json';
    $('#toc')
        // 検索条件入力エリアを目次の一番上に挿入
        .prepend(
            '<div id="search-area">' +
                '<form id="search-form">' +
                    '<div class="slinput">' +
                        '<i class="fa fa-search left-icon"></i>' +
                        '<input  id="search-query" placeholder="全文検索" />' +
                        '<i class="fa fa-close right-icon"></i>' +
                    '</div>' +
                    '<input id="search-start" type="hidden" value="0"/>' +
                    '<input id="search-num" type="hidden" value="20"/>' +
                '<form>' +
            '</div>')
        .ready(function() {
            $searchArea = $(this);

            // 入力項目の検索条件でEnterを押したら検索処理を実行する
            $searchArea.find('#search-form').submit({navi:0}, doSearch);

            // 検索条件に値がない場合はバツアイコンを薄くする
            $searchArea.find("#search-query").keyup(function(){
              var val = $(this).val();
              if(val.length > 0) {
                 $(this).parent().find(".right-icon").css('color','#555');
              } else {
                $(this).parent().find(".right-icon").css('color','#ccc');
              }
            });

            // バツアイコンを押すと検索条件をクリアする
            $searchArea.find(".right-icon").click(function(){
              $(this).parent().find("input").val('');
              $(this).css('color','#ccc');
            });
        });

    $('#header>h1')
        // 検索結果エリアをドキュメントタイトル直下に挿入
        .before(
            '<div id="search-result-area">' +
                '<div id="subheader"></div>' +
                '<div id="result"></div>' +
            '</div>')
        .ready(function() {
            $(this)
                .find('#search-result-area')
                    // 検索結果エリアのバツアイコンをクリックしたら検索領域を削除する
                    .on("click", '#remove-search-result', function(e) {
                      $searchResultArea = $(e.delegateTarget)

                      $searchResultArea.removeClass('show');
                      $searchResultArea.find('#subheader').empty();
                      $searchResultArea.find('#result').empty();
                    })
                    // 前ページリンクをクリックしたら１ページ前を検索する
                    .on("click", "#prevPageLink", {navi:-1}, doSearch)
                    // 次ページリンクをクリックしたら１ページ後を検索する
                    .on("click", "#nextPageLink", {navi:1}, doSearch);
        });



    /**
     * 検索処理
     *
     * @param  {eventObject} event
     * @return {boolean}  submit処理を中断させるために必ずfalseを返却する
     */
    function doSearch(event){
      // 検索フィールドの値をトリムして取得
      var searchQuery = $.trim($('#search-query').val());
      // 空の場合は検索処理を実行しない
      if(searchQuery.length == 0) {
        return false;
      }


      // 表示開始位置、表示件数の取得
      var start = parseInt($('#search-start').val()),
          num = parseInt($('#search-num').val());
      // 表示開始位置のチェック
      if(start < 0) {
        start = 0;
      }
      // 表示件数のチェック
      if(num < 1 || num > 100) {
        num = 20;
      }
      // 表示ページ情報の取得
      switch(event.data.navi) {
        case -1:
          // 前のページの場合
          start -= num;
          break;
        case 1:
          // 次のページの場合
          start += num;
          break;
        default:
        case 0:
          start = 0;
          break;
      }


      // URLを構築
      var url = FESS_JSON_ENDPOINT + '?callback=?' + // 別ドメインを想定してJSONP形式でリクエストを送信する
                                     '&q=' + encodeURIComponent(searchQuery) +
                                     '&start=' + start + '&num=' + num;

      // 検索リクエスト送信
      // 別ドメインを想定してJSONP形式でリクエストを送信する
      $.ajax({
          url: url,
          dataType: 'jsonp',
          success: renderSearchResult
      });


      // ページ情報の更新
      $('#searchNum').val(num);

      // ページ表示を上部に移動
      $(document).scrollTop(0);

      // サブミットを抑止するためにfalseを返す
      return false;
    };


    /**
     * 検索成功時に検索結果を描画する
     *
     * @param  {Anything} data レスポンスデータ
     */
    function renderSearchResult(data) {
      // 検索結果処理
      var dataResponse = data.response;
      // ステータスチェック
      if(dataResponse.status != 0) {
        alert("検索中に問題が発生しました。");
        return;
      }

      // 検索結果領域を表示する
      $('#search-result-area').addClass('show');

      var $subheader = $('#subheader'),
          $result = $('#result'),
          record_count = dataResponse.record_count;

      // 検索結果がない場合
      if(record_count == 0) {
        // サブヘッダーに出力
        $subheader[0].innerHTML =  '<div id="remove-search-result" style="float:right;"><i class="fa fa-times"></i></div>';

        // 結果領域に出力
        $result[0].innerHTML = '<b>' + dataResponse.q + '</b>に一致する情報は見つかりませんでした。';

        return;
      }


      // 検索にヒットした場合
      var page_number = dataResponse.page_number,
          page_size = dataResponse.page_size,
          page_count = dataResponse.page_count,
          startRange = (page_number - 1) * page_size + 1,
          endRange = page_number * page_size,
          i = 0,
          max;
      offset = startRange - 1;
      $('#searchStart').val(offset);



      // サブヘッダーに出力
      $subheader[0].innerHTML = '<b>' + dataResponse.q + '</b> の検索結果 ' +
                                record_count + " 件中 " +  startRange + ' - ' +
                                endRange + ' 件目 (' + dataResponse.exec_time + ' 秒)' +
                               '<div id="remove-search-result" style="float:right;"><i class="fa fa-times"></i></div>'

      // 検索結果領域のクリア
      $result.empty();


      // 検索結果の出力
      var $resultBody = $("<ol/>");
      var results = dataResponse.result;
      for(i = 0, max = results.length; i < max; i++) {
        var element =
            '<li>' +
                '<h4 class="title">' +
                    '<a href="' +results[i].url_link + '">' + results[i].title + '</a>' +
                '</h4>' +
                '<div class="body">' +
                    results[i].content_description +
                    '<br/>' +
                    '<cite>' + results[i].site + '</cite>' +
                '</div>' +
            '</li>';

        $(element).appendTo($resultBody);
      }
      $resultBody.appendTo($result);


      // ページ番号情報の出力
      var pageArea = [];
      pageArea.push('<div id="pageInfo">', page_number, 'ページ目<br/>');
      if(page_number > 1) {
        // 前のページへのリンク
        pageArea.push('<a id="prevPageLink" href="#">&lt;&lt;前ページへ</a> ');
      }
      if(page_number < page_count) {
        // 次のページへのリンク
        pageArea.push('<a id="nextPageLink" href="#">次ページへ&gt;&gt;</a>');
      }
      pageArea.push('</div>');
      $(pageArea.join("")).appendTo($result);
    }
});
