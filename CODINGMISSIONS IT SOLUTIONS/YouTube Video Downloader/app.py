from flask import Flask, render_template, request
from pytube import YouTube
import os

app = Flask(__name__, template_folder='.')

VIDEO_DOWNLOAD_DIR = 'E:/New folder/videos'
MP3_DOWNLOAD_DIR = 'E:/New folder/music'

os.makedirs(VIDEO_DOWNLOAD_DIR, exist_ok=True)
os.makedirs(MP3_DOWNLOAD_DIR, exist_ok=True)

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/download', methods=['GET', 'POST'])
def download():
    if request.method == 'GET':
        return redirect('/')
    
    yt_url = request.form['youtube_url']
    action = request.form['action']
    
    try:
        if action == 'audio':
            audio(yt_url)
        elif action == 'video':
            video(yt_url)
        return render_template("success.html", message=f"{action.title()} download completed!")
    except Exception as e:
        return f"An error occurred: {str(e)}"


def audio(yt_url):
    yt = YouTube(yt_url)
    ya = yt.streams.get_audio_only()
    ya.download(output_path=MP3_DOWNLOAD_DIR)
    print(f"Audio downloaded: {yt.title}")

def video(yt_url):
    yt = YouTube(yt_url)
    yv = yt.streams.get_highest_resolution()
    yv.download(output_path=VIDEO_DOWNLOAD_DIR)
    print(f"Video downloaded: {yt.title}")

if __name__ == '__main__':
    app.run(debug=True)
