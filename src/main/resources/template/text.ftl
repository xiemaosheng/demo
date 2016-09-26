<!DOCTYPE html>
<html lang="zh">
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <style type="text/css">
        body {
            margin-left: 45px;
            margin-right: 45px;
            font-family: Arial Unicode MS;
            font-size: 10px;
        }

        table {
            margin: auto;
            width: 100%;
            border-collapse: collapse;
            border: 1px solid #444444;
        }

        th,td {
            border: 1px solid #444444;
            font-size: 10px;
            margin-left: 5px;
        }

        .mcContent {
            line-height: 180%;
            padding: 20px;
        }

        .logo {
            text-align: center;
        }

        .title {
            text-align: center;
            font-weight: bold;
            font-size: 20px;
        }

        .notes {
            font-weight: normal;
            margin-left: 5px;
            margin-right: 5px;
            line-height: 18px;
        }

        .text_content {
            margin-left: 5px;
            margin-right: 5px;
            line-height: 18px;
        }

        .sum_insured_first_row {
            width: 20%;
        }

        .sum_insured_span {
            font-size: 10px;
        }

        .special_agreements_div {
            page-break-before: always;
            font-size: 14px;
            margin-top: 20px;
        }

        .special_agreements_div .special_agreements {
            font-size: 18px;
            font-weight: bold;
        }

        .title_right {
            width: 100%;
            margin: 0 auto;
        }

        .title_right p {
            text-align: left;
            margin: 0;
            margin-left: 50%;
            padding: 0;
        }

        @page {
            size: 8.5in 11in;
        @
        bottom-center
        {
            content
            :
                    "page "
                    counter(
                            page
                    )
                    " of  "
                    counter(
                            pages
                    );
        }

        .signature {
            margin: 0 auto;
            clear: both;
            font-size: 16px;
            font-weight: bold;
        }

        .signature_table {
            /*     font-size: 16px; */
            font-weight: bold;
        }

    </style>
</head>
<body>
<div>
    <p>Hello：${username}</p>
    <div class="logo"><!--这里的图片使用相对与ITextRenderer.getSharedContext().setBaseURL("file:/"+imageDiskPath);的路径-->
      <img src="logo1.png" />
    </div>
    <div>
        <p></p>
        <div style="border:1px solid red;color:red;">

        </div>
        <div style="border:10px solid blue;color:blue;">

        </div>
        <hr/>
        <table>
            <tr style="background:gray;">
                <th>A</th>
                <th>B</th>
                <th>C</th>
                <th>D</th>
            </tr>
            <tr>
                <td>100</td>
                <td>29</td>
                <td>32</td>
                <td>43</td>
            </tr>
            <tr>
                <td>100</td>
                <td>29</td>
                <td>32</td>
                <td>43</td>
            </tr>
            <tr>
                <td>100</td>
                <td>29</td>
                <td>32</td>
                <td>43</td>
            </tr>
            <tr>
                <td>100</td>
                <td>29</td>
                <td>32</td>
                <td>43</td>
            </tr>
            <tr>
                <td>100</td>
                <td>29</td>
                <td>32</td>
                <td>43</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>