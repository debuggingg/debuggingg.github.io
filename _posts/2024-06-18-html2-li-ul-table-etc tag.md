---
layout: single
title: 2024/06/18/ HTML -02-li ,ul ,ol ,dl ,table,map,iframe,audio,video etc
---
## Tag(il, ul,ol,dl,table....)
1. **General:**
    
    - style tag: A tag to apply styles to HTML document tags
    - type attribute: Sets the type (MimeType) of document for applying styles to HTML tags 
2. **li Tag:**
    
    - li tag: A tag for displaying lists
    - => Only used as a child tag of ol or ul tags
3. **ul Tag:**
    
    - ul tag: A tag to display a list with bullets (symbols)
4. **ol Tag:**
    
    - ol tag: A tag to display a list with order values
5. **dl Tag:**
    
    - dl tag: A tag for displaying definition lists
    - => Child tags: dt tag, dd tag
6. **dt Tag:**
    
    - dt tag: A tag for displaying definition terms
7. **dd Tag:**
    
    - dd tag: A tag for displaying definition descriptions
8. **table Tag:**
    
    - table tag: A tag for displaying tables
    - => Child tags: tr tag
    - => All tr tags should have the same number of th(td) tags for consistency
9. **tr Tag:**
    
    - tr tag: A tag for displaying rows in a table
    - => Child tags: th tag, td tag
10. **th and td Tags:**
    
    - th(td) tag: A tag for displaying columns in a row
    - => th tag contents are centered and bolded - used for column names
    - => The content determines the width of the column
11. **tbody Tag:**
    
    - HTML5: thead tag (header), tbody tag (body), tfoot tag (footer) used for structural representation of tables
    - => tr tag order doesn't matter; header, body, footer are displayed in order
    - summary attribute: Sets a description of the table for screen readers
12. **caption Tag:**
    
    - caption tag: A tag for displaying the title of a table
13. **tfoot Tag:**
    
    - tfoot tag: A tag for displaying the footer of a table
14. **thead Tag:**
    
    - thead tag: A tag for displaying the header of a table
15. **Attributes:**
    
    - rowspan attribute: Sets the number of rows to merge
    - colspan attribute: Sets the number of columns to merge

This organization keeps the HTML code and comments separated, making it easier to understand and manage both.

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HTML</title>
<style type="text/css">
table {
    border: 1px solid black;
    border-collapse: collapse;
}

th, td {
    border: 1px solid black;
    width: 100px;
    padding: 20px;
    text-align: center;
}

caption {
    font-size: 24px;
    line-height: 40px;
    letter-spacing: 15px;
}
</style>
</head>
<body>
<h1>li Tag</h1>
<hr>
<h2>Yu-Chae Room</h2>
<ul>
    <li>Dormitory, one team of the same sex (or family)</li>
    <li>4-person room</li>
    <li>Shared kitchen and bathroom</li>
    <li>Cost
        <ul>
            <li>Off-season: 20,000 KRW per person</li>
            <li>Peak season: 25,000 KRW per person</li>
        </ul>
    </li>
</ul>
<hr>
<h2>Yu-Chae Room</h2>
<ol>
    <li>Dormitory, one team of the same sex (or family)</li>
    <li>4-person room</li>
    <li>Shared kitchen and bathroom</li>
    <li>Cost
        <ol>
            <li>Off-season: 20,000 KRW per person</li>
            <li>Peak season: 25,000 KRW per person</li>
        </ol>
    </li>
</ol>
<h1>dl Tag</h1>
<hr>
<h2>Yu-Chae Room</h2>
<dl>
    <dt>Target</dt>
    <dd>Dormitory, one team of the same sex (or family)</dd>

    <dt>Size</dt>
    <dd>4-person room</dd>

    <dt>Notes</dt>
    <dd>Shared kitchen and bathroom</dd>

    <dt>Cost</dt>
    <dd>Off-season: 20,000 KRW per person</dd>
    <dd>Peak season: 25,000 KRW per person</dd>
</dl>
<h1>table Tag</h1>
<hr>
<table border="1">
    <tr>
        <th>Number</th><th>Name</th><th>Address</th>
    </tr>
    <tr>
        <td>1000</td><td>Hong Gil-dong</td><td>Gangnam-gu, Seoul</td>
    </tr>
    <tr>
        <td>2000</td><td>Hong Gil-dong</td><td>Paldal-gu, Suwon</td>
    </tr>
    <tr>
        <td>3000</td><td>Jeon Woo-chi</td><td>Wolmi-gu, Incheon</td>
    </tr>
</table>
<h1>tbody Tag</h1>
<hr>
<table summary="Room Information">
    <caption>Room List</caption>
    <tfoot>
        <tr>
            <td colspan="4">The entire outer building is rented as a single unit.</td>
        </tr>
    </tfoot>
    <thead>
        <tr>
            <th>Building Name</th><th>Room Name</th><th>Size</th><th>Price</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td rowspan="3">Yoandora</td><td>Yu-Chae Room</td><td>4-person room</td><td rowspan="2">20,000 KRW</td>
        </tr>
        <tr>
            <td>Dong-Baek Room</td><td>2-person room</td>
        </tr>
        <tr>
            <td>Family Room</td><td colspan="2">60,000 KRW (up to 4 people)</td>
        </tr>
    </tbody>
</table>
</body>
</html>
```

---
## Tag (map, iframe,audio,video)

- **usemap Attribute:**
    
    -  usemap attribute: Sets the name attribute value of the map tag as its value 
- **map Tag:**
    
    -  map tag: A tag to divide the displayed image into areas 
    -  => Child tag: area tag 
    -  name attribute: Sets a name to distinguish the tag 
- **area Tag:**
    
    -  area tag: A tag to provide hyperlink functionality by setting areas on an image 
    -  shape attribute: Sets the shape to divide the area as its value 
    -  coords attribute: Sets the coordinates of the shape as its value 
- **iframe Tag:**
    
    -  iframe tag: A tag to create an embedded browser to request a web document (web program) and display the response 
- **src Attribute:**
    
    -  src attribute: Sets the URL address of the web document (web program) requested by the embedded browser as its value 
- **audio Tag:**
    
    -  audio tag: A tag to play sound files stored on the server 
    -  => embed tag: Plays flash files, object tag: Plays multimedia files 
    -  => Sound files used on the web: mp3 files, ogg files, etc. 
    -  autoplay attribute: Provides the autoplay function - value can be omitted 
    -  controls attribute: Provides a control panel for playback - value can be omitted 
    -  loop attribute: Provides the repeat play function - value can be omitted 
    -  preload attribute: Sets one of none, metadata, auto (default) as its value 
- **source Tag:**
    
    -  source tag: A tag to provide multimedia files 
    -  => Plays various types of sound files 
    -  type attribute: Sets the file type (MimeType) as its value 
- **video Tag:**
    
    -  video tag: A tag to play video files stored on the server 
    -  => Video files used on the web: mp4 files, ogg files (ogv files), webm files, etc. 
    - 
```html
<body>
	<h1>map Tag</h1>
	<img alt="Image Link" src="/web/html/images/map.png" usemap="#favorites">

	<map name="favorites">
		<area alt="Twitter" shape="rect" coords="0,0,129,117" href="https://twitter.com" target="_blank">
		<area alt="Facebook" shape="rect" coords="129,0,249,117" href="https://facebook.com" target="_blank">
	</map>
	
	<h1>iframe Tag</h1>
	<hr>
	<iframe src="/web/html/10_left.html" width="600" height="500" name="left"></iframe>
	<iframe src="/web/html/10_right.html" width="600" height="500" name="right"></iframe>
	
	<h1>audio Tag</h1>
	<hr>
	<audio controls>
		<source src="/web/html/audio/horse.ogg" type="audio/ogg">
		<source src="/web/html/audio/horse.mp3" type="audio/mp3">
	</audio>
	
	<h1>video Tag</h1>
	<hr>
	<video src="/web/html/video/movie.mp4" autoplay controls width="600"></video>
</body>
```

- main pages send the icon to left, and left send the link to right pages 
	-left and right is all small windows in the main window
```html
<body>

<h1>left pages</h1>

<hr>

<p><a href="https://www.daum.net" target="right">다음</a></p>
```
```html
<body>

<h1>right pages</h1>

<hr>

</body>
```
