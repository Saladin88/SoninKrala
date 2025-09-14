import {Component, NgZone, inject, OnDestroy} from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { PronunciationService } from './service/pronunciation.service';
import { ResultPronunciation } from './Model/pronunciations';
import {Subscription} from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import recordConfig from '../../config/record-config.json';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-record-audio',
  imports: [ MatIconModule, ReactiveFormsModule, MatButtonModule,CommonModule],
  templateUrl: './record-audio.component.html',
  styleUrls: ['./record-audio.component.css']
})
export class RecordAudioComponent implements OnDestroy {
  pronunciationService = inject(PronunciationService);
  route = inject(ActivatedRoute);
  private readonly subscriptions: Subscription[] = [];
  wordTitle : string = this.route.snapshot.params['word'];
  hintStartAudio : string = recordConfig.config.hint.recordInstruction;
  sendButton : string = recordConfig.config.creation.actions.send;
  scoreLabel : string = recordConfig.config.label.score;
  resultPronunciation : ResultPronunciation | null = null;
  audioChunks : Blob[] = [];
  recordedAudioBlob! : Blob;
  mediaRecorder! : MediaRecorder;
  timer! : number;
  recorderStatus : RecordingState = 'inactive';
  audioUrl: string = "";
  analyser! : AnalyserNode;
  zone = inject(NgZone);

  toggleRecording(event : MouseEvent) {
    if(event.isTrusted && this.recorderStatus) {
      if(this.recorderStatus === 'recording') {
        this.stopRecording()
      } else {
        this.audioUrl = '';
        this.resultPronunciation = null;
        this.startRecording()
      }
    }
  }
  async startRecording() {
    try {
      this.audioChunks = [];
      const stream = await navigator.mediaDevices.getUserMedia({audio : true})
     this.mediaRecorder = new MediaRecorder(stream);


      this.mediaRecorder.ondataavailable = event => {
        console.log(event);
        console.log('this is the chunk = ', this.audioChunks)
        this.audioChunks.push(event.data)
        console.log('this is the chunk after data push = ', this.audioChunks)
      }

      this.mediaRecorder.onstop = () => {
        this.zone.run(()=> {
          const audioBlob = new Blob(this.audioChunks, {type : 'audio/webm'});
          this.audioUrl = URL.createObjectURL(audioBlob);
          this.recordedAudioBlob = audioBlob;
          this.recorderStatus = 'inactive';
          this.audioChunks = [];
        })
      };
      this.mediaRecorder.start();
      this.recorderStatus = 'recording'
    } catch (err) {
      console.error(err)
    }

  }

  stopRecording() {
    if(this.mediaRecorder && this.recorderStatus !== 'inactive') {
      this.mediaRecorder.stop();
    }
  }

  sendRecord(event : MouseEvent) {
    event.preventDefault()
    if( event.isTrusted && this.recordedAudioBlob) {
      console.log('Audio chunks before sending:', this.audioChunks);
      console.log('Audio URL:', this.audioUrl);
      const { mime, ext } = this.pickAudioMime();
      const fileName = `${this.wordTitle}.${ext}`
      const audioFile = new File([this.recordedAudioBlob], fileName, { type: mime });
      const recordSub = this.pronunciationService.sendVoicePronunciation(this.wordTitle,this.createFormData(audioFile)).subscribe({
        next: (response : ResultPronunciation) => {
          console.log('Audio sent successfully:', response);
          this.audioUrl = '';
          this.resultPronunciation = response;
        },
        error: (error) => {
          console.error('Error sending audio:', error);
        }
      })
      this.subscriptions.push(recordSub);
    }
  }

  createFormData(audioFile : File) : FormData {
    const formData = new FormData();
    formData.append('audioFile', audioFile, audioFile.name);
    formData.append('audioFileName', this.wordTitle);
    console.log('this is formdata = ',formData );
    console.log('Contenu du FormData :');
for (const pair of formData.entries()) {
  console.log(`${pair[0]}:`, pair[1]);
}
    return formData
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe());
  }
  pickAudioMime(): { mime: string; ext: string } {
    if (MediaRecorder.isTypeSupported('audio/webm;codecs=opus')) return { mime:'audio/webm', ext:'webm' };
    if (MediaRecorder.isTypeSupported('audio/mp4;codecs=aac'))  return { mime:'audio/mp4',  ext:'m4a'  }; // Safari/iOS
    return { mime:'audio/webm', ext:'webm' };
  }
}
