package org.jaudiotagger.issues;

import org.jaudiotagger.AbstractTestCase;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.id3.ID3v22Tag;
import org.jaudiotagger.tag.id3.ID3v24Frame;
import org.jaudiotagger.tag.id3.ID3v24Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.reference.Languages;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Support For Custom fields
 */
public class Issue298Test extends AbstractTestCase
{
    /**
     * Test writing Custom fields
     */
    @Test    public void  testWriteFieldsToMp3ID3v24()
    {
        File testFile = null;
        Exception exceptionCaught = null;
        try
        {
            testFile = AbstractTestCase.copyAudioToTmp("testV1.mp3");

            //Read File okay
            AudioFile af = AudioFileIO.read(testFile);
            af.setTag(new ID3v24Tag());
            Tag tag = af.getTag();
            tag.setField(tag.createField(FieldKey.CUSTOM1,"custom1"));
            tag.setField(tag.createField(FieldKey.CUSTOM2,"custom2"));
            tag.setField(tag.createField(FieldKey.CUSTOM3,"custom3"));
            tag.setField(tag.createField(FieldKey.CUSTOM4,"custom4"));
            tag.setField(tag.createField(FieldKey.CUSTOM5,"custom5"));
            tag.setField(tag.createField(FieldKey.FBPM,"155.5"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID,"c1f657ba-8177-3cbb-b84a-f62bc684a94e"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID,"c1f657ba-8177-3cbb-b84a-f62bc684a94f"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_WORK_ID,"0410c22a-0b2b-4793-9f18-5f1fab36338e"));
            tag.setField(tag.createField(FieldKey.OCCASION,"Occasion"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_ALBUM,"original_album"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_LYRICIST,"original_lyricist"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_ARTIST,"original_artist"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_YEAR,"2012"));
            tag.setField(tag.createField(FieldKey.QUALITY,"quality"));
            tag.setField(tag.createField(FieldKey.SCRIPT,"latin"));
            tag.setField(tag.createField(FieldKey.TAGS,"fred"));
            tag.setField(tag.createField(FieldKey.TEMPO,"Mellow"));
            tag.setField(tag.createField(FieldKey.MOOD,"Bad Mood"));
            tag.setField(tag.createField(FieldKey.MOOD_AGGRESSIVE,"60"));
            tag.setField(tag.createField(FieldKey.MOOD_RELAXED,"61"));
            tag.setField(tag.createField(FieldKey.MOOD_SAD,"62"));
            tag.setField(tag.createField(FieldKey.MOOD_HAPPY,"63"));
            tag.setField(tag.createField(FieldKey.MOOD_PARTY,"64"));
            tag.setField(tag.createField(FieldKey.MOOD_DANCEABILITY,"65"));
            tag.setField(tag.createField(FieldKey.MOOD_VALENCE,"66"));
            tag.setField(tag.createField(FieldKey.MOOD_AROUSAL,"67"));
            tag.setField(tag.createField(FieldKey.ACOUSTIC,"68"));
            tag.setField(tag.createField(FieldKey.ELECTRONIC,"69"));
            tag.setField(tag.createField(FieldKey.INSTRUMENTAL,"70"));
            tag.setField(tag.createField(FieldKey.TIMBRE,"71"));
            tag.setField(tag.createField(FieldKey.TONALITY,"72"));
            tag.setField(tag.createField(FieldKey.KEY,"Am"));
            tag.setField(tag.createField(FieldKey.ORCHESTRA,"Orchestra"));
            tag.setField(tag.createField(FieldKey.PART,"Part"));
            tag.setField(tag.createField(FieldKey.WORK,"Work"));
            tag.setField(tag.createField(FieldKey.PERFORMER,"Performer"));
            assertEquals("custom1",af.getTag().getFirst(FieldKey.CUSTOM1));
            assertEquals("custom2",af.getTag().getFirst(FieldKey.CUSTOM2));
            assertEquals("custom3",af.getTag().getFirst(FieldKey.CUSTOM3));
            assertEquals("custom4",af.getTag().getFirst(FieldKey.CUSTOM4));
            assertEquals("custom5",af.getTag().getFirst(FieldKey.CUSTOM5));
            assertEquals("155.5",af.getTag().getFirst(FieldKey.FBPM));
            assertEquals("c1f657ba-8177-3cbb-b84a-f62bc684a94e",af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID));
            assertEquals("c1f657ba-8177-3cbb-b84a-f62bc684a94f", af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID));
            assertEquals("0410c22a-0b2b-4793-9f18-5f1fab36338e",af.getTag().getFirst(FieldKey.MUSICBRAINZ_WORK_ID));
            assertEquals("Occasion",af.getTag().getFirst(FieldKey.OCCASION));
            assertEquals("original_album",af.getTag().getFirst(FieldKey.ORIGINAL_ALBUM));
            assertEquals("original_lyricist",af.getTag().getFirst(FieldKey.ORIGINAL_LYRICIST));
            assertEquals("original_artist",af.getTag().getFirst(FieldKey.ORIGINAL_ARTIST));
            assertEquals("2012",af.getTag().getFirst(FieldKey.ORIGINAL_YEAR));
            assertEquals("quality",af.getTag().getFirst(FieldKey.QUALITY));
            assertEquals("latin",af.getTag().getFirst(FieldKey.SCRIPT));
            assertEquals("fred",af.getTag().getFirst(FieldKey.TAGS));
            assertEquals("Mellow",af.getTag().getFirst(FieldKey.TEMPO));
            assertEquals("Bad Mood",af.getTag().getFirst(FieldKey.MOOD));
            assertEquals("60",af.getTag().getFirst(FieldKey.MOOD_AGGRESSIVE));
            assertEquals("61",af.getTag().getFirst(FieldKey.MOOD_RELAXED));
            assertEquals("62",af.getTag().getFirst(FieldKey.MOOD_SAD));
            assertEquals("63",af.getTag().getFirst(FieldKey.MOOD_HAPPY));
            assertEquals("64",af.getTag().getFirst(FieldKey.MOOD_PARTY));
            assertEquals("65",af.getTag().getFirst(FieldKey.MOOD_DANCEABILITY));
            assertEquals("66",af.getTag().getFirst(FieldKey.MOOD_VALENCE));
            assertEquals("67",af.getTag().getFirst(FieldKey.MOOD_AROUSAL));
            assertEquals("68",af.getTag().getFirst(FieldKey.ACOUSTIC));
            assertEquals("69",af.getTag().getFirst(FieldKey.ELECTRONIC));
            assertEquals("70",af.getTag().getFirst(FieldKey.INSTRUMENTAL));
            assertEquals("71",af.getTag().getFirst(FieldKey.TIMBRE));
            assertEquals("72",af.getTag().getFirst(FieldKey.TONALITY));
            assertEquals("Am",af.getTag().getFirst(FieldKey.KEY));
            assertEquals("Orchestra",af.getTag().getFirst(FieldKey.ORCHESTRA));
            assertEquals("Part",af.getTag().getFirst(FieldKey.PART));
            assertEquals("Work",af.getTag().getFirst(FieldKey.WORK));
            assertEquals("Performer",af.getTag().getFirst(FieldKey.PERFORMER));

            {
                TagField tagField = af.getTag().getFirstField(FieldKey.CUSTOM1);
                assertTrue(tagField instanceof ID3v24Frame);
                assertTrue(((ID3v24Frame)tagField).getBody() instanceof FrameBodyCOMM);
            }
            af.commit();
            af = AudioFileIO.read(testFile);
            tag = af.getTag();
            assertEquals("custom1",af.getTag().getFirst(FieldKey.CUSTOM1));
            assertEquals("custom2",af.getTag().getFirst(FieldKey.CUSTOM2));
            assertEquals("custom3",af.getTag().getFirst(FieldKey.CUSTOM3));
            assertEquals("custom4",af.getTag().getFirst(FieldKey.CUSTOM4));
            assertEquals("custom5",af.getTag().getFirst(FieldKey.CUSTOM5));
            assertEquals("155.5",af.getTag().getFirst(FieldKey.FBPM));
            assertEquals("c1f657ba-8177-3cbb-b84a-f62bc684a94e",af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID));
            assertEquals("c1f657ba-8177-3cbb-b84a-f62bc684a94f", af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID));
            assertEquals("0410c22a-0b2b-4793-9f18-5f1fab36338e",af.getTag().getFirst(FieldKey.MUSICBRAINZ_WORK_ID));
            assertEquals("Occasion",af.getTag().getFirst(FieldKey.OCCASION));
            assertEquals("original_album",af.getTag().getFirst(FieldKey.ORIGINAL_ALBUM));
            assertEquals("original_lyricist",af.getTag().getFirst(FieldKey.ORIGINAL_LYRICIST));
            assertEquals("original_artist",af.getTag().getFirst(FieldKey.ORIGINAL_ARTIST));
            assertEquals("2012",af.getTag().getFirst(FieldKey.ORIGINAL_YEAR));
            assertEquals("quality",af.getTag().getFirst(FieldKey.QUALITY));
            assertEquals("latin",af.getTag().getFirst(FieldKey.SCRIPT));
            assertEquals("fred",af.getTag().getFirst(FieldKey.TAGS));
            assertEquals("Mellow",af.getTag().getFirst(FieldKey.TEMPO));
            assertEquals("Bad Mood",af.getTag().getFirst(FieldKey.MOOD));
            assertEquals("60",af.getTag().getFirst(FieldKey.MOOD_AGGRESSIVE));
            assertEquals("61",af.getTag().getFirst(FieldKey.MOOD_RELAXED));
            assertEquals("62",af.getTag().getFirst(FieldKey.MOOD_SAD));
            assertEquals("63",af.getTag().getFirst(FieldKey.MOOD_HAPPY));
            assertEquals("64",af.getTag().getFirst(FieldKey.MOOD_PARTY));
            assertEquals("65",af.getTag().getFirst(FieldKey.MOOD_DANCEABILITY));
            assertEquals("66",af.getTag().getFirst(FieldKey.MOOD_VALENCE));
            assertEquals("67",af.getTag().getFirst(FieldKey.MOOD_AROUSAL));
            assertEquals("68",af.getTag().getFirst(FieldKey.ACOUSTIC));
            assertEquals("69",af.getTag().getFirst(FieldKey.ELECTRONIC));
            assertEquals("70",af.getTag().getFirst(FieldKey.INSTRUMENTAL));
            assertEquals("71",af.getTag().getFirst(FieldKey.TIMBRE));
            assertEquals("72",af.getTag().getFirst(FieldKey.TONALITY));
            assertEquals("Am",af.getTag().getFirst(FieldKey.KEY));
            assertEquals("Orchestra",af.getTag().getFirst(FieldKey.ORCHESTRA));
            assertEquals("Part",af.getTag().getFirst(FieldKey.PART));
            assertEquals("Work",af.getTag().getFirst(FieldKey.WORK));
            assertEquals("Performer",af.getTag().getFirst(FieldKey.PERFORMER));

            {
                TagField tagField = af.getTag().getFirstField(FieldKey.CUSTOM1);
                assertTrue(tagField instanceof ID3v24Frame);
                assertTrue(((ID3v24Frame)tagField).getBody() instanceof FrameBodyCOMM);
                FrameBodyCOMM body =(FrameBodyCOMM)((ID3v24Frame)tagField).getBody();
                assertEquals(FrameBodyCOMM.MM_CUSTOM1,body.getDescription());
                assertEquals(Languages.MEDIA_MONKEY_ID,body.getLanguage());
            }
            {
                TagField tagField = af.getTag().getFirstField(FieldKey.CUSTOM2);
                assertTrue(tagField instanceof ID3v24Frame);
                assertTrue(((ID3v24Frame)tagField).getBody() instanceof FrameBodyCOMM);
                FrameBodyCOMM body =(FrameBodyCOMM)((ID3v24Frame)tagField).getBody();
                assertEquals(FrameBodyCOMM.MM_CUSTOM2,body.getDescription());
                assertEquals(Languages.MEDIA_MONKEY_ID,body.getLanguage());
            }
            {
                TagField tagField = af.getTag().getFirstField(FieldKey.CUSTOM3);
                assertTrue(tagField instanceof ID3v24Frame);
                assertTrue(((ID3v24Frame)tagField).getBody() instanceof FrameBodyCOMM);
                FrameBodyCOMM body =(FrameBodyCOMM)((ID3v24Frame)tagField).getBody();
                assertEquals(FrameBodyCOMM.MM_CUSTOM3,body.getDescription());
                assertEquals(Languages.MEDIA_MONKEY_ID,body.getLanguage());
            }
            {
                TagField tagField = af.getTag().getFirstField(FieldKey.CUSTOM4);
                assertTrue(tagField instanceof ID3v24Frame);
                assertTrue(((ID3v24Frame)tagField).getBody() instanceof FrameBodyCOMM);
                FrameBodyCOMM body =(FrameBodyCOMM)((ID3v24Frame)tagField).getBody();
                assertEquals(FrameBodyCOMM.MM_CUSTOM4,body.getDescription());
                assertEquals(Languages.MEDIA_MONKEY_ID,body.getLanguage());
            }
            {
                TagField tagField = af.getTag().getFirstField(FieldKey.CUSTOM5);
                assertTrue(tagField instanceof ID3v24Frame);
                assertTrue(((ID3v24Frame)tagField).getBody() instanceof FrameBodyCOMM);
                FrameBodyCOMM body =(FrameBodyCOMM)((ID3v24Frame)tagField).getBody();
                assertEquals(FrameBodyCOMM.MM_CUSTOM5,body.getDescription());
                assertEquals(Languages.MEDIA_MONKEY_ID,body.getLanguage());
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
            exceptionCaught=e;
        }

        assertNull(exceptionCaught);
    }

     /**
     * Test writing Custom fields
     */
    @Test
    public void testWriteFieldsToMp3ID3v23()
    {
        File testFile = null;
        Exception exceptionCaught = null;
        try
        {
            testFile = AbstractTestCase.copyAudioToTmp("testV1.mp3");

            //Read File okay
            AudioFile af = AudioFileIO.read(testFile);
            af.getTagOrCreateAndSetDefault();
            Tag tag = af.getTag();
            tag.setField(tag.createField(FieldKey.CUSTOM1,"custom1"));
            tag.setField(tag.createField(FieldKey.CUSTOM2,"custom2"));
            tag.setField(tag.createField(FieldKey.CUSTOM3,"custom3"));
            tag.setField(tag.createField(FieldKey.CUSTOM4,"custom4"));
            tag.setField(tag.createField(FieldKey.CUSTOM5,"custom5"));
            tag.setField(tag.createField(FieldKey.FBPM,"155.5"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID,"c1f657ba-8177-3cbb-b84a-f62bc684a94e"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID,"c1f657ba-8177-3cbb-b84a-f62bc684a94f"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_WORK_ID,"0410c22a-0b2b-4793-9f18-5f1fab36338e"));
            tag.setField(tag.createField(FieldKey.OCCASION,"Occasion"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_ALBUM,"original_album"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_LYRICIST,"original_lyricist"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_ARTIST,"original_artist"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_YEAR,"2012"));
            tag.setField(tag.createField(FieldKey.QUALITY,"quality"));
            tag.setField(tag.createField(FieldKey.SCRIPT,"latin"));
            tag.setField(tag.createField(FieldKey.TAGS,"fred"));
            tag.setField(tag.createField(FieldKey.TEMPO,"Mellow"));
            tag.setField(tag.createField(FieldKey.MOOD,"Bad Mood"));
            tag.setField(tag.createField(FieldKey.MOOD,"Bad Mood"));
            tag.setField(tag.createField(FieldKey.MOOD_AGGRESSIVE,"60"));
            tag.setField(tag.createField(FieldKey.MOOD_RELAXED,"61"));
            tag.setField(tag.createField(FieldKey.MOOD_SAD,"62"));
            tag.setField(tag.createField(FieldKey.MOOD_HAPPY,"63"));
            tag.setField(tag.createField(FieldKey.MOOD_PARTY,"64"));
            tag.setField(tag.createField(FieldKey.MOOD_DANCEABILITY,"65"));
            tag.setField(tag.createField(FieldKey.MOOD_VALENCE,"66"));
            tag.setField(tag.createField(FieldKey.MOOD_AROUSAL,"67"));
            tag.setField(tag.createField(FieldKey.ACOUSTIC,"68"));
            tag.setField(tag.createField(FieldKey.ELECTRONIC,"69"));
            tag.setField(tag.createField(FieldKey.INSTRUMENTAL,"70"));
            tag.setField(tag.createField(FieldKey.TIMBRE,"71"));
            tag.setField(tag.createField(FieldKey.TONALITY,"72"));
            tag.setField(tag.createField(FieldKey.KEY,"Am"));
            tag.setField(tag.createField(FieldKey.ORCHESTRA,"Orchestra"));
            tag.setField(tag.createField(FieldKey.PART,"Part"));
            tag.setField(tag.createField(FieldKey.WORK,"Work"));
            tag.setField(tag.createField(FieldKey.PERFORMER,"Performer"));



            af.commit();
            af = AudioFileIO.read(testFile);
            assertEquals("custom1",af.getTag().getFirst(FieldKey.CUSTOM1));
            assertEquals("custom2",af.getTag().getFirst(FieldKey.CUSTOM2));
            assertEquals("custom3",af.getTag().getFirst(FieldKey.CUSTOM3));
            assertEquals("custom4",af.getTag().getFirst(FieldKey.CUSTOM4));
            assertEquals("custom5",af.getTag().getFirst(FieldKey.CUSTOM5));
            assertEquals("155.5",af.getTag().getFirst(FieldKey.FBPM));
            assertEquals("c1f657ba-8177-3cbb-b84a-f62bc684a94e",af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID));
            assertEquals("c1f657ba-8177-3cbb-b84a-f62bc684a94f", af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID));
            assertEquals("0410c22a-0b2b-4793-9f18-5f1fab36338e",af.getTag().getFirst(FieldKey.MUSICBRAINZ_WORK_ID));
            assertEquals("Occasion",af.getTag().getFirst(FieldKey.OCCASION));
            assertEquals("original_album",af.getTag().getFirst(FieldKey.ORIGINAL_ALBUM));
            assertEquals("original_lyricist",af.getTag().getFirst(FieldKey.ORIGINAL_LYRICIST));
            assertEquals("original_artist",af.getTag().getFirst(FieldKey.ORIGINAL_ARTIST));
            assertEquals("2012",af.getTag().getFirst(FieldKey.ORIGINAL_YEAR));
            assertEquals("quality",af.getTag().getFirst(FieldKey.QUALITY));
            assertEquals("latin",af.getTag().getFirst(FieldKey.SCRIPT));
            assertEquals("fred",af.getTag().getFirst(FieldKey.TAGS));
            assertEquals("Mellow",af.getTag().getFirst(FieldKey.TEMPO));
            assertEquals("Bad Mood",af.getTag().getFirst(FieldKey.MOOD));
            assertEquals("60",af.getTag().getFirst(FieldKey.MOOD_AGGRESSIVE));
            assertEquals("61",af.getTag().getFirst(FieldKey.MOOD_RELAXED));
            assertEquals("62",af.getTag().getFirst(FieldKey.MOOD_SAD));
            assertEquals("63",af.getTag().getFirst(FieldKey.MOOD_HAPPY));
            assertEquals("64",af.getTag().getFirst(FieldKey.MOOD_PARTY));
            assertEquals("65",af.getTag().getFirst(FieldKey.MOOD_DANCEABILITY));
            assertEquals("66",af.getTag().getFirst(FieldKey.MOOD_VALENCE));
            assertEquals("67",af.getTag().getFirst(FieldKey.MOOD_AROUSAL));
            assertEquals("68",af.getTag().getFirst(FieldKey.ACOUSTIC));
            assertEquals("69",af.getTag().getFirst(FieldKey.ELECTRONIC));
            assertEquals("70",af.getTag().getFirst(FieldKey.INSTRUMENTAL));
            assertEquals("71",af.getTag().getFirst(FieldKey.TIMBRE));
            assertEquals("72",af.getTag().getFirst(FieldKey.TONALITY));
            assertEquals("Am",af.getTag().getFirst(FieldKey.KEY));
            assertEquals("Orchestra",af.getTag().getFirst(FieldKey.ORCHESTRA));
            assertEquals("Part",af.getTag().getFirst(FieldKey.PART));
            assertEquals("Work",af.getTag().getFirst(FieldKey.WORK));
            assertEquals("Performer",af.getTag().getFirst(FieldKey.PERFORMER));




        }
        catch(Exception e)
        {
            e.printStackTrace();
            exceptionCaught=e;
        }

        assertNull(exceptionCaught);
    }

     /**
     * Test writing Custom fields to Mp3 ID3v23
     */
    @Test
    public void testWriteFieldsToMp3ID3v22()
    {
        File testFile = null;
        Exception exceptionCaught = null;
        try
        {
            testFile = AbstractTestCase.copyAudioToTmp("testV1.mp3");

            //Read File okay
            AudioFile af = AudioFileIO.read(testFile);
            af.setTag(new ID3v22Tag());
            Tag tag = af.getTag();
            tag.setField(tag.createField(FieldKey.CUSTOM1,"custom1"));
            tag.setField(tag.createField(FieldKey.CUSTOM2,"custom2"));
            tag.setField(tag.createField(FieldKey.CUSTOM3,"custom3"));
            tag.setField(tag.createField(FieldKey.CUSTOM4,"custom4"));
            tag.setField(tag.createField(FieldKey.CUSTOM5,"custom5"));
            tag.setField(tag.createField(FieldKey.FBPM,"155.5"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID,"c1f657ba-8177-3cbb-b84a-f62bc684a94e"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID,"c1f657ba-8177-3cbb-b84a-f62bc684a94f"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_WORK_ID,"0410c22a-0b2b-4793-9f18-5f1fab36338e"));
            tag.setField(tag.createField(FieldKey.OCCASION,"Occasion"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_ALBUM,"original_album"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_LYRICIST,"original_lyricist"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_ARTIST,"original_artist"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_YEAR,"2012"));
            tag.setField(tag.createField(FieldKey.QUALITY,"quality"));
            tag.setField(tag.createField(FieldKey.SCRIPT,"latin"));
            tag.setField(tag.createField(FieldKey.TAGS,"fred"));
            tag.setField(tag.createField(FieldKey.TEMPO,"Mellow"));
            tag.setField(tag.createField(FieldKey.MOOD,"Bad Mood"));
            tag.setField(tag.createField(FieldKey.MOOD,"Bad Mood"));
            tag.setField(tag.createField(FieldKey.MOOD_AGGRESSIVE,"60"));
            tag.setField(tag.createField(FieldKey.MOOD_RELAXED,"61"));
            tag.setField(tag.createField(FieldKey.MOOD_SAD,"62"));
            tag.setField(tag.createField(FieldKey.MOOD_HAPPY,"63"));
            tag.setField(tag.createField(FieldKey.MOOD_PARTY,"64"));
            tag.setField(tag.createField(FieldKey.MOOD_DANCEABILITY,"65"));
            tag.setField(tag.createField(FieldKey.MOOD_VALENCE,"66"));
            tag.setField(tag.createField(FieldKey.MOOD_AROUSAL,"67"));
            tag.setField(tag.createField(FieldKey.ACOUSTIC,"68"));
            tag.setField(tag.createField(FieldKey.ELECTRONIC,"69"));
            tag.setField(tag.createField(FieldKey.INSTRUMENTAL,"70"));
            tag.setField(tag.createField(FieldKey.TIMBRE,"71"));
            tag.setField(tag.createField(FieldKey.TONALITY,"72"));
            tag.setField(tag.createField(FieldKey.KEY,"Am"));
            tag.setField(tag.createField(FieldKey.ORCHESTRA,"Orchestra"));
            tag.setField(tag.createField(FieldKey.PART,"Part"));
            tag.setField(tag.createField(FieldKey.WORK,"Work"));
            tag.setField(tag.createField(FieldKey.PERFORMER,"Performer"));


            af.commit();
            af = AudioFileIO.read(testFile);
            assertEquals("custom1",af.getTag().getFirst(FieldKey.CUSTOM1));
            assertEquals("custom2",af.getTag().getFirst(FieldKey.CUSTOM2));
            assertEquals("custom3",af.getTag().getFirst(FieldKey.CUSTOM3));
            assertEquals("custom4",af.getTag().getFirst(FieldKey.CUSTOM4));
            assertEquals("custom5",af.getTag().getFirst(FieldKey.CUSTOM5));
            assertEquals("155.5",af.getTag().getFirst(FieldKey.FBPM));
            assertEquals("c1f657ba-8177-3cbb-b84a-f62bc684a94e",af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID));
            assertEquals("c1f657ba-8177-3cbb-b84a-f62bc684a94f", af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID));
            assertEquals("0410c22a-0b2b-4793-9f18-5f1fab36338e",af.getTag().getFirst(FieldKey.MUSICBRAINZ_WORK_ID));
            assertEquals("Occasion",af.getTag().getFirst(FieldKey.OCCASION));
            assertEquals("original_album",af.getTag().getFirst(FieldKey.ORIGINAL_ALBUM));
            assertEquals("original_lyricist",af.getTag().getFirst(FieldKey.ORIGINAL_LYRICIST));
            assertEquals("original_artist",af.getTag().getFirst(FieldKey.ORIGINAL_ARTIST));
            assertEquals("2012",af.getTag().getFirst(FieldKey.ORIGINAL_YEAR));
            assertEquals("quality",af.getTag().getFirst(FieldKey.QUALITY));
            assertEquals("latin",af.getTag().getFirst(FieldKey.SCRIPT));
            assertEquals("fred",af.getTag().getFirst(FieldKey.TAGS));
            assertEquals("Mellow",af.getTag().getFirst(FieldKey.TEMPO));
            assertEquals("Bad Mood",af.getTag().getFirst(FieldKey.MOOD));
            assertEquals("60",af.getTag().getFirst(FieldKey.MOOD_AGGRESSIVE));
            assertEquals("61",af.getTag().getFirst(FieldKey.MOOD_RELAXED));
            assertEquals("62",af.getTag().getFirst(FieldKey.MOOD_SAD));
            assertEquals("63",af.getTag().getFirst(FieldKey.MOOD_HAPPY));
            assertEquals("64",af.getTag().getFirst(FieldKey.MOOD_PARTY));
            assertEquals("65",af.getTag().getFirst(FieldKey.MOOD_DANCEABILITY));
            assertEquals("66",af.getTag().getFirst(FieldKey.MOOD_VALENCE));
            assertEquals("67",af.getTag().getFirst(FieldKey.MOOD_AROUSAL));
            assertEquals("68",af.getTag().getFirst(FieldKey.ACOUSTIC));
            assertEquals("69",af.getTag().getFirst(FieldKey.ELECTRONIC));
            assertEquals("70",af.getTag().getFirst(FieldKey.INSTRUMENTAL));
            assertEquals("71",af.getTag().getFirst(FieldKey.TIMBRE));
            assertEquals("72",af.getTag().getFirst(FieldKey.TONALITY));
            assertEquals("Am",af.getTag().getFirst(FieldKey.KEY));
            assertEquals("Orchestra",af.getTag().getFirst(FieldKey.ORCHESTRA));
            assertEquals("Part",af.getTag().getFirst(FieldKey.PART));
            assertEquals("Work",af.getTag().getFirst(FieldKey.WORK));
            assertEquals("Performer",af.getTag().getFirst(FieldKey.PERFORMER));

        }
        catch(Exception e)
        {
            e.printStackTrace();
            exceptionCaught=e;
        }

        assertNull(exceptionCaught);
    }

     /**
     * Test writing Custom fields to Ogg Vorbis
     */
    @Test
    public void testWriteFieldsToOggVorbis()
    {
        File testFile = null;
        Exception exceptionCaught = null;
        try
        {
            testFile = AbstractTestCase.copyAudioToTmp("test.ogg");

            //Read File okay
            AudioFile af = AudioFileIO.read(testFile);
            Tag tag = af.getTag();
            tag.setField(tag.createField(FieldKey.CUSTOM1,"custom1"));
            tag.setField(tag.createField(FieldKey.CUSTOM2,"custom2"));
            tag.setField(tag.createField(FieldKey.CUSTOM3,"custom3"));
            tag.setField(tag.createField(FieldKey.CUSTOM4,"custom4"));
            tag.setField(tag.createField(FieldKey.CUSTOM5,"custom5"));
            tag.setField(tag.createField(FieldKey.FBPM,"155.5"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID,"c1f657ba-8177-3cbb-b84a-f62bc684a94e"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID,"c1f657ba-8177-3cbb-b84a-f62bc684a94f"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_WORK_ID,"0410c22a-0b2b-4793-9f18-5f1fab36338e"));
            tag.setField(tag.createField(FieldKey.OCCASION,"Occasion"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_ALBUM,"original_album"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_LYRICIST,"original_lyricist"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_ARTIST,"original_artist"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_YEAR,"2012"));
            tag.setField(tag.createField(FieldKey.QUALITY,"quality"));
            tag.setField(tag.createField(FieldKey.SCRIPT,"latin"));
            tag.setField(tag.createField(FieldKey.TAGS,"fred"));
            tag.setField(tag.createField(FieldKey.TEMPO,"Mellow"));
            tag.setField(tag.createField(FieldKey.MOOD,"Bad Mood"));
            tag.setField(tag.createField(FieldKey.MOOD_AGGRESSIVE,"60"));
            tag.setField(tag.createField(FieldKey.MOOD_RELAXED,"61"));
            tag.setField(tag.createField(FieldKey.MOOD_SAD,"62"));
            tag.setField(tag.createField(FieldKey.MOOD_HAPPY,"63"));
            tag.setField(tag.createField(FieldKey.MOOD_PARTY,"64"));
            tag.setField(tag.createField(FieldKey.MOOD_DANCEABILITY,"65"));
            tag.setField(tag.createField(FieldKey.MOOD_VALENCE,"66"));
            tag.setField(tag.createField(FieldKey.MOOD_AROUSAL,"67"));
            tag.setField(tag.createField(FieldKey.ACOUSTIC,"68"));
            tag.setField(tag.createField(FieldKey.ELECTRONIC,"69"));
            tag.setField(tag.createField(FieldKey.INSTRUMENTAL,"70"));
            tag.setField(tag.createField(FieldKey.TIMBRE,"71"));
            tag.setField(tag.createField(FieldKey.TONALITY,"72"));
            tag.setField(tag.createField(FieldKey.KEY,"Am"));
            tag.setField(tag.createField(FieldKey.ORCHESTRA,"Orchestra"));
            tag.setField(tag.createField(FieldKey.PART,"Part"));
            tag.setField(tag.createField(FieldKey.WORK,"Work"));
            tag.setField(tag.createField(FieldKey.PERFORMER,"Performer"));


            af.commit();
            af = AudioFileIO.read(testFile);
            assertEquals("custom1",af.getTag().getFirst(FieldKey.CUSTOM1));
            assertEquals("custom2",af.getTag().getFirst(FieldKey.CUSTOM2));
            assertEquals("custom3",af.getTag().getFirst(FieldKey.CUSTOM3));
            assertEquals("custom4",af.getTag().getFirst(FieldKey.CUSTOM4));
            assertEquals("custom5",af.getTag().getFirst(FieldKey.CUSTOM5));
            assertEquals("155.5",af.getTag().getFirst(FieldKey.FBPM));
            assertEquals("c1f657ba-8177-3cbb-b84a-f62bc684a94e",af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID));
            assertEquals("c1f657ba-8177-3cbb-b84a-f62bc684a94f", af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID));
            assertEquals("0410c22a-0b2b-4793-9f18-5f1fab36338e",af.getTag().getFirst(FieldKey.MUSICBRAINZ_WORK_ID));
            assertEquals("Occasion",af.getTag().getFirst(FieldKey.OCCASION));
            assertEquals("original_album",af.getTag().getFirst(FieldKey.ORIGINAL_ALBUM));
            assertEquals("original_lyricist",af.getTag().getFirst(FieldKey.ORIGINAL_LYRICIST));
            assertEquals("original_artist",af.getTag().getFirst(FieldKey.ORIGINAL_ARTIST));
            assertEquals("2012",af.getTag().getFirst(FieldKey.ORIGINAL_YEAR));
            assertEquals("quality",af.getTag().getFirst(FieldKey.QUALITY));
            assertEquals("latin",af.getTag().getFirst(FieldKey.SCRIPT));
            assertEquals("fred",af.getTag().getFirst(FieldKey.TAGS));
            assertEquals("Mellow",af.getTag().getFirst(FieldKey.TEMPO));
            assertEquals("Bad Mood",af.getTag().getFirst(FieldKey.MOOD));
            assertEquals("60",af.getTag().getFirst(FieldKey.MOOD_AGGRESSIVE));
            assertEquals("61",af.getTag().getFirst(FieldKey.MOOD_RELAXED));
            assertEquals("62",af.getTag().getFirst(FieldKey.MOOD_SAD));
            assertEquals("63",af.getTag().getFirst(FieldKey.MOOD_HAPPY));
            assertEquals("64",af.getTag().getFirst(FieldKey.MOOD_PARTY));
            assertEquals("65",af.getTag().getFirst(FieldKey.MOOD_DANCEABILITY));
            assertEquals("66",af.getTag().getFirst(FieldKey.MOOD_VALENCE));
            assertEquals("67",af.getTag().getFirst(FieldKey.MOOD_AROUSAL));
            assertEquals("68",af.getTag().getFirst(FieldKey.ACOUSTIC));
            assertEquals("69",af.getTag().getFirst(FieldKey.ELECTRONIC));
            assertEquals("70",af.getTag().getFirst(FieldKey.INSTRUMENTAL));
            assertEquals("71",af.getTag().getFirst(FieldKey.TIMBRE));
            assertEquals("72",af.getTag().getFirst(FieldKey.TONALITY));
            assertEquals("Am",af.getTag().getFirst(FieldKey.KEY));
            assertEquals("Orchestra",af.getTag().getFirst(FieldKey.ORCHESTRA));
            assertEquals("Part",af.getTag().getFirst(FieldKey.PART));
            assertEquals("Work",af.getTag().getFirst(FieldKey.WORK));
            assertEquals("Performer",af.getTag().getFirst(FieldKey.PERFORMER));

        }
        catch(Exception e)
        {
            e.printStackTrace();
            exceptionCaught=e;
        }

        assertNull(exceptionCaught);
    }

    /**
     * Test writing Custom fields to Flac
     */
    @Test
    public void testWriteFieldsToFlac()
    {
        File testFile = null;
        Exception exceptionCaught = null;
        try
        {
            testFile = AbstractTestCase.copyAudioToTmp("test.flac");

            //Read File okay
            AudioFile af = AudioFileIO.read(testFile);
            Tag tag = af.getTag();
            tag.setField(tag.createField(FieldKey.CUSTOM1,"custom1"));
            tag.setField(tag.createField(FieldKey.CUSTOM2,"custom2"));
            tag.setField(tag.createField(FieldKey.CUSTOM3,"custom3"));
            tag.setField(tag.createField(FieldKey.CUSTOM4,"custom4"));
            tag.setField(tag.createField(FieldKey.CUSTOM5,"custom5"));
            tag.setField(tag.createField(FieldKey.FBPM,"155.5"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID,"c1f657ba-8177-3cbb-b84a-f62bc684a94e"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID,"c1f657ba-8177-3cbb-b84a-f62bc684a94f"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_WORK_ID,"0410c22a-0b2b-4793-9f18-5f1fab36338e"));
            tag.setField(tag.createField(FieldKey.OCCASION,"Occasion"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_ALBUM,"original_album"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_LYRICIST,"original_lyricist"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_ARTIST,"original_artist"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_YEAR,"2012"));
            tag.setField(tag.createField(FieldKey.QUALITY,"quality"));
            tag.setField(tag.createField(FieldKey.SCRIPT,"latin"));
            tag.setField(tag.createField(FieldKey.TAGS,"fred"));
            tag.setField(tag.createField(FieldKey.TEMPO,"Mellow"));
            tag.setField(tag.createField(FieldKey.MOOD,"Bad Mood"));
            tag.setField(tag.createField(FieldKey.MOOD_AGGRESSIVE,"60"));
            tag.setField(tag.createField(FieldKey.MOOD_RELAXED,"61"));
            tag.setField(tag.createField(FieldKey.MOOD_SAD,"62"));
            tag.setField(tag.createField(FieldKey.MOOD_HAPPY,"63"));
            tag.setField(tag.createField(FieldKey.MOOD_PARTY,"64"));
            tag.setField(tag.createField(FieldKey.MOOD_DANCEABILITY,"65"));
            tag.setField(tag.createField(FieldKey.MOOD_VALENCE,"66"));
            tag.setField(tag.createField(FieldKey.MOOD_AROUSAL,"67"));
            tag.setField(tag.createField(FieldKey.ACOUSTIC,"68"));
            tag.setField(tag.createField(FieldKey.ELECTRONIC,"69"));
            tag.setField(tag.createField(FieldKey.INSTRUMENTAL,"70"));
            tag.setField(tag.createField(FieldKey.TIMBRE,"71"));
            tag.setField(tag.createField(FieldKey.TONALITY,"72"));
            tag.setField(tag.createField(FieldKey.KEY,"Am"));
            tag.setField(tag.createField(FieldKey.ORCHESTRA,"Orchestra"));
            tag.setField(tag.createField(FieldKey.PART,"Part"));
            tag.setField(tag.createField(FieldKey.WORK,"Work"));
            tag.setField(tag.createField(FieldKey.PERFORMER,"Performer"));

            af.commit();

            af = AudioFileIO.read(testFile);
            assertEquals("custom1",af.getTag().getFirst(FieldKey.CUSTOM1));
            assertEquals("custom2",af.getTag().getFirst(FieldKey.CUSTOM2));
            assertEquals("custom3",af.getTag().getFirst(FieldKey.CUSTOM3));
            assertEquals("custom4",af.getTag().getFirst(FieldKey.CUSTOM4));
            assertEquals("custom5",af.getTag().getFirst(FieldKey.CUSTOM5));
            assertEquals("155.5",af.getTag().getFirst(FieldKey.FBPM));
            assertEquals("c1f657ba-8177-3cbb-b84a-f62bc684a94e",af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID));
            assertEquals("c1f657ba-8177-3cbb-b84a-f62bc684a94f", af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID));
            assertEquals("0410c22a-0b2b-4793-9f18-5f1fab36338e",af.getTag().getFirst(FieldKey.MUSICBRAINZ_WORK_ID));
            assertEquals("Occasion",af.getTag().getFirst(FieldKey.OCCASION));
            assertEquals("original_album",af.getTag().getFirst(FieldKey.ORIGINAL_ALBUM));
            assertEquals("original_lyricist",af.getTag().getFirst(FieldKey.ORIGINAL_LYRICIST));
            assertEquals("original_artist",af.getTag().getFirst(FieldKey.ORIGINAL_ARTIST));
            assertEquals("2012",af.getTag().getFirst(FieldKey.ORIGINAL_YEAR));
            assertEquals("quality",af.getTag().getFirst(FieldKey.QUALITY));
            assertEquals("latin",af.getTag().getFirst(FieldKey.SCRIPT));
            assertEquals("fred",af.getTag().getFirst(FieldKey.TAGS));
            assertEquals("Mellow",af.getTag().getFirst(FieldKey.TEMPO));
            assertEquals("Bad Mood",af.getTag().getFirst(FieldKey.MOOD));
            assertEquals("60",af.getTag().getFirst(FieldKey.MOOD_AGGRESSIVE));
            assertEquals("61",af.getTag().getFirst(FieldKey.MOOD_RELAXED));
            assertEquals("62",af.getTag().getFirst(FieldKey.MOOD_SAD));
            assertEquals("63",af.getTag().getFirst(FieldKey.MOOD_HAPPY));
            assertEquals("64",af.getTag().getFirst(FieldKey.MOOD_PARTY));
            assertEquals("65",af.getTag().getFirst(FieldKey.MOOD_DANCEABILITY));
            assertEquals("66",af.getTag().getFirst(FieldKey.MOOD_VALENCE));
            assertEquals("67",af.getTag().getFirst(FieldKey.MOOD_AROUSAL));
            assertEquals("68",af.getTag().getFirst(FieldKey.ACOUSTIC));
            assertEquals("69",af.getTag().getFirst(FieldKey.ELECTRONIC));
            assertEquals("70",af.getTag().getFirst(FieldKey.INSTRUMENTAL));
            assertEquals("71",af.getTag().getFirst(FieldKey.TIMBRE));
            assertEquals("72",af.getTag().getFirst(FieldKey.TONALITY));
            assertEquals("Am",af.getTag().getFirst(FieldKey.KEY));
            assertEquals("Orchestra",af.getTag().getFirst(FieldKey.ORCHESTRA));
            assertEquals("Part",af.getTag().getFirst(FieldKey.PART));
            assertEquals("Work",af.getTag().getFirst(FieldKey.WORK));
            assertEquals("Performer",af.getTag().getFirst(FieldKey.PERFORMER));

        }
        catch(Exception e)
        {
            e.printStackTrace();
            exceptionCaught=e;
        }

        assertNull(exceptionCaught);
    }



    /**
     * Test writing Custom fields to Wma
     */
    @Test
    public void testWriteFieldsToWma()
    {
        File testFile = null;
        Exception exceptionCaught = null;
        try
        {
            testFile = AbstractTestCase.copyAudioToTmp("test1.wma");

            //Read File okay
            AudioFile af = AudioFileIO.read(testFile);
            Tag tag = af.getTag();
            tag.setField(tag.createField(FieldKey.CUSTOM1,"custom1"));
            tag.setField(tag.createField(FieldKey.CUSTOM2,"custom2"));
            tag.setField(tag.createField(FieldKey.CUSTOM3,"custom3"));
            tag.setField(tag.createField(FieldKey.CUSTOM4,"custom4"));
            tag.setField(tag.createField(FieldKey.CUSTOM5,"custom5"));
            tag.setField(tag.createField(FieldKey.FBPM,"155.5"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID,"c1f657ba-8177-3cbb-b84a-f62bc684a94e"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID,"c1f657ba-8177-3cbb-b84a-f62bc684a94f"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_WORK_ID,"0410c22a-0b2b-4793-9f18-5f1fab36338e"));
            tag.setField(tag.createField(FieldKey.OCCASION,"Occasion"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_ALBUM,"original_album"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_LYRICIST,"original_lyricist"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_ARTIST,"original_artist"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_YEAR,"2012"));
            tag.setField(tag.createField(FieldKey.QUALITY,"quality"));
            tag.setField(tag.createField(FieldKey.SCRIPT,"latin"));
            tag.setField(tag.createField(FieldKey.TAGS,"fred"));
            tag.setField(tag.createField(FieldKey.TEMPO,"Mellow"));
            tag.setField(tag.createField(FieldKey.MOOD,"Bad Mood"));
            tag.setField(tag.createField(FieldKey.MOOD_AGGRESSIVE,"60"));
            tag.setField(tag.createField(FieldKey.MOOD_RELAXED,"61"));
            tag.setField(tag.createField(FieldKey.MOOD_SAD,"62"));
            tag.setField(tag.createField(FieldKey.MOOD_HAPPY,"63"));
            tag.setField(tag.createField(FieldKey.MOOD_PARTY,"64"));
            tag.setField(tag.createField(FieldKey.MOOD_DANCEABILITY,"65"));
            tag.setField(tag.createField(FieldKey.MOOD_VALENCE,"66"));
            tag.setField(tag.createField(FieldKey.MOOD_AROUSAL,"67"));
            tag.setField(tag.createField(FieldKey.ACOUSTIC,"68"));
            tag.setField(tag.createField(FieldKey.ELECTRONIC,"69"));
            tag.setField(tag.createField(FieldKey.INSTRUMENTAL,"70"));
            tag.setField(tag.createField(FieldKey.TIMBRE,"71"));
            tag.setField(tag.createField(FieldKey.TONALITY,"72"));
            tag.setField(tag.createField(FieldKey.KEY,"Am"));
            tag.setField(tag.createField(FieldKey.ORCHESTRA,"Orchestra"));
            tag.setField(tag.createField(FieldKey.PART,"Part"));
            tag.setField(tag.createField(FieldKey.WORK,"Work"));
            tag.setField(tag.createField(FieldKey.PERFORMER,"Performer"));


            af.commit();
            af = AudioFileIO.read(testFile);
            assertEquals("custom1",af.getTag().getFirst(FieldKey.CUSTOM1));
            assertEquals("custom2",af.getTag().getFirst(FieldKey.CUSTOM2));
            assertEquals("custom3",af.getTag().getFirst(FieldKey.CUSTOM3));
            assertEquals("custom4",af.getTag().getFirst(FieldKey.CUSTOM4));
            assertEquals("custom5",af.getTag().getFirst(FieldKey.CUSTOM5));
            assertEquals("155.5",af.getTag().getFirst(FieldKey.FBPM));
            assertEquals("c1f657ba-8177-3cbb-b84a-f62bc684a94e",af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID));
            assertEquals("c1f657ba-8177-3cbb-b84a-f62bc684a94f", af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID));
            assertEquals("0410c22a-0b2b-4793-9f18-5f1fab36338e",af.getTag().getFirst(FieldKey.MUSICBRAINZ_WORK_ID));
            assertEquals("Occasion",af.getTag().getFirst(FieldKey.OCCASION));
            assertEquals("original_album",af.getTag().getFirst(FieldKey.ORIGINAL_ALBUM));
            assertEquals("original_lyricist",af.getTag().getFirst(FieldKey.ORIGINAL_LYRICIST));
            assertEquals("original_artist",af.getTag().getFirst(FieldKey.ORIGINAL_ARTIST));
            assertEquals("2012",af.getTag().getFirst(FieldKey.ORIGINAL_YEAR));
            assertEquals("quality",af.getTag().getFirst(FieldKey.QUALITY));
            assertEquals("latin",af.getTag().getFirst(FieldKey.SCRIPT));
            assertEquals("fred",af.getTag().getFirst(FieldKey.TAGS));
            assertEquals("Mellow",af.getTag().getFirst(FieldKey.TEMPO));
            assertEquals("Bad Mood",af.getTag().getFirst(FieldKey.MOOD));
            assertEquals("60",af.getTag().getFirst(FieldKey.MOOD_AGGRESSIVE));
            assertEquals("61",af.getTag().getFirst(FieldKey.MOOD_RELAXED));
            assertEquals("62",af.getTag().getFirst(FieldKey.MOOD_SAD));
            assertEquals("63",af.getTag().getFirst(FieldKey.MOOD_HAPPY));
            assertEquals("64",af.getTag().getFirst(FieldKey.MOOD_PARTY));
            assertEquals("65",af.getTag().getFirst(FieldKey.MOOD_DANCEABILITY));
            assertEquals("66",af.getTag().getFirst(FieldKey.MOOD_VALENCE));
            assertEquals("67",af.getTag().getFirst(FieldKey.MOOD_AROUSAL));
            assertEquals("68",af.getTag().getFirst(FieldKey.ACOUSTIC));
            assertEquals("69",af.getTag().getFirst(FieldKey.ELECTRONIC));
            assertEquals("70",af.getTag().getFirst(FieldKey.INSTRUMENTAL));
            assertEquals("71",af.getTag().getFirst(FieldKey.TIMBRE));
            assertEquals("72",af.getTag().getFirst(FieldKey.TONALITY));
            assertEquals("Am",af.getTag().getFirst(FieldKey.KEY));
            assertEquals("Orchestra",af.getTag().getFirst(FieldKey.ORCHESTRA));
            assertEquals("Part",af.getTag().getFirst(FieldKey.PART));
            assertEquals("Work",af.getTag().getFirst(FieldKey.WORK));
            assertEquals("Performer",af.getTag().getFirst(FieldKey.PERFORMER));

        }
        catch(Exception e)
        {
            e.printStackTrace();
            exceptionCaught=e;
        }

        assertNull(exceptionCaught);
    }

     /**
     * Test writing Custom fields to Mp4
     */
    @Test
    public void testWriteFieldsToMp4()
    {
        File testFile = null;
        Exception exceptionCaught = null;
        try
        {
            testFile = AbstractTestCase.copyAudioToTmp("test.m4a");

            //Read File okay
            AudioFile af = AudioFileIO.read(testFile);
            Tag tag = af.getTag();
            tag.setField(tag.createField(FieldKey.CUSTOM1,"custom1"));
            tag.setField(tag.createField(FieldKey.CUSTOM2,"custom2"));
            tag.setField(tag.createField(FieldKey.CUSTOM3,"custom3"));
            tag.setField(tag.createField(FieldKey.CUSTOM4,"custom4"));
            tag.setField(tag.createField(FieldKey.CUSTOM5,"custom5"));
            tag.setField(tag.createField(FieldKey.FBPM,"155.5"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID,"c1f657ba-8177-3cbb-b84a-f62bc684a94e"));

            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID,"c1f657ba-8177-3cbb-b84a-f62bc684a94f"));
            tag.setField(tag.createField(FieldKey.MUSICBRAINZ_WORK_ID,"0410c22a-0b2b-4793-9f18-5f1fab36338e"));
            tag.setField(tag.createField(FieldKey.OCCASION,"Occasion"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_ALBUM,"original_album"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_LYRICIST,"original_lyricist"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_ARTIST,"original_artist"));
            tag.setField(tag.createField(FieldKey.ORIGINAL_YEAR,"2012"));
            tag.setField(tag.createField(FieldKey.QUALITY,"quality"));
            tag.setField(tag.createField(FieldKey.SCRIPT,"latin"));
            tag.setField(tag.createField(FieldKey.TAGS,"fred"));
            tag.setField(tag.createField(FieldKey.TEMPO,"Mellow"));
            tag.setField(tag.createField(FieldKey.MOOD,"Bad Mood"));
            tag.setField(tag.createField(FieldKey.MOOD_AGGRESSIVE,"60"));
            tag.setField(tag.createField(FieldKey.MOOD_RELAXED,"61"));
            tag.setField(tag.createField(FieldKey.MOOD_SAD,"62"));
            tag.setField(tag.createField(FieldKey.MOOD_HAPPY,"63"));
            tag.setField(tag.createField(FieldKey.MOOD_PARTY,"64"));
            tag.setField(tag.createField(FieldKey.MOOD_DANCEABILITY,"65"));
            tag.setField(tag.createField(FieldKey.MOOD_VALENCE,"66"));
            tag.setField(tag.createField(FieldKey.MOOD_AROUSAL,"67"));
            tag.setField(tag.createField(FieldKey.ACOUSTIC,"68"));
            tag.setField(tag.createField(FieldKey.ELECTRONIC,"69"));
            tag.setField(tag.createField(FieldKey.INSTRUMENTAL,"70"));
            tag.setField(tag.createField(FieldKey.TIMBRE,"71"));
            tag.setField(tag.createField(FieldKey.TONALITY,"72"));
            tag.setField(tag.createField(FieldKey.KEY,"Am"));
            tag.setField(tag.createField(FieldKey.ORCHESTRA,"Orchestra"));
            tag.setField(tag.createField(FieldKey.PART,"Part"));
            tag.setField(tag.createField(FieldKey.WORK,"Work"));
            tag.setField(tag.createField(FieldKey.PERFORMER,"Performer"));

            af.commit();
            af = AudioFileIO.read(testFile);
            assertEquals("custom1",af.getTag().getFirst(FieldKey.CUSTOM1));
            assertEquals("custom2",af.getTag().getFirst(FieldKey.CUSTOM2));
            assertEquals("custom3",af.getTag().getFirst(FieldKey.CUSTOM3));
            assertEquals("custom4",af.getTag().getFirst(FieldKey.CUSTOM4));
            assertEquals("custom5",af.getTag().getFirst(FieldKey.CUSTOM5));
            assertEquals("155.5",af.getTag().getFirst(FieldKey.FBPM));
            assertEquals("c1f657ba-8177-3cbb-b84a-f62bc684a94e",af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASE_GROUP_ID));
            assertEquals("c1f657ba-8177-3cbb-b84a-f62bc684a94f", af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASE_TRACK_ID));
            assertEquals("0410c22a-0b2b-4793-9f18-5f1fab36338e",af.getTag().getFirst(FieldKey.MUSICBRAINZ_WORK_ID));
            assertEquals("Occasion",af.getTag().getFirst(FieldKey.OCCASION));
            assertEquals("original_album",af.getTag().getFirst(FieldKey.ORIGINAL_ALBUM));
            assertEquals("original_lyricist",af.getTag().getFirst(FieldKey.ORIGINAL_LYRICIST));
            assertEquals("original_artist",af.getTag().getFirst(FieldKey.ORIGINAL_ARTIST));
            assertEquals("2012",af.getTag().getFirst(FieldKey.ORIGINAL_YEAR));
            assertEquals("quality",af.getTag().getFirst(FieldKey.QUALITY));
            assertEquals("latin",af.getTag().getFirst(FieldKey.SCRIPT));
            assertEquals("fred",af.getTag().getFirst(FieldKey.TAGS));
            assertEquals("Mellow",af.getTag().getFirst(FieldKey.TEMPO));
            assertEquals("Bad Mood",af.getTag().getFirst(FieldKey.MOOD));
            assertEquals("60",af.getTag().getFirst(FieldKey.MOOD_AGGRESSIVE));
            assertEquals("61",af.getTag().getFirst(FieldKey.MOOD_RELAXED));
            assertEquals("62",af.getTag().getFirst(FieldKey.MOOD_SAD));
            assertEquals("63",af.getTag().getFirst(FieldKey.MOOD_HAPPY));
            assertEquals("64",af.getTag().getFirst(FieldKey.MOOD_PARTY));
            assertEquals("65",af.getTag().getFirst(FieldKey.MOOD_DANCEABILITY));
            assertEquals("66",af.getTag().getFirst(FieldKey.MOOD_VALENCE));
            assertEquals("67",af.getTag().getFirst(FieldKey.MOOD_AROUSAL));
            assertEquals("68",af.getTag().getFirst(FieldKey.ACOUSTIC));
            assertEquals("69",af.getTag().getFirst(FieldKey.ELECTRONIC));
            assertEquals("70",af.getTag().getFirst(FieldKey.INSTRUMENTAL));
            assertEquals("71",af.getTag().getFirst(FieldKey.TIMBRE));
            assertEquals("72",af.getTag().getFirst(FieldKey.TONALITY));
            assertEquals("Am",af.getTag().getFirst(FieldKey.KEY));
            assertEquals("Orchestra",af.getTag().getFirst(FieldKey.ORCHESTRA));
            assertEquals("Part",af.getTag().getFirst(FieldKey.PART));
            assertEquals("Work",af.getTag().getFirst(FieldKey.WORK));
            assertEquals("Performer",af.getTag().getFirst(FieldKey.PERFORMER));

        }
        catch(Exception e)
        {
            e.printStackTrace();
            exceptionCaught=e;
        }

        assertNull(exceptionCaught);
    }




}