using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using StackExchange.Redis;
//using System.Windows.Forms;

namespace testCSharp
{
    /// <summary>
    /// MainWindow.xaml 的交互逻辑
    /// </summary>
    public partial class MainWindow : Window
    {
        public delegate void TestContentChange();
        ConnectionMultiplexer redis;
        String messgse="";

        public MainWindow()
        {
            InitializeComponent();
            Console.Out.WriteLine("hello world");
            this.redis= ConnectionMultiplexer.Connect("localhost");
            
        }

        public void setButtonText()
        {
            testText.Text = messgse;
        }

        private void setContent()
        {
          //return;
           // }
        }

        private void testButton_Click(object sender, RoutedEventArgs e)
        {
            
            IDatabase db = redis.GetDatabase();
            string value = "start:"+ DateTime.Now.ToLongTimeString();
            db.StringSet("mykey", value);
            RedisValue redisValue = db.StringGet("mykey");
            testText.Text=redisValue.ToString();
            ISubscriber sub = redis.GetSubscriber();
            sub.Subscribe("messages", (channel, message) => {
                this.messgse = (string)message + " @ " + DateTime.Now.ToLongTimeString();
                Console.WriteLine(this.messgse);

                testButton.Dispatcher.BeginInvoke(
                   System.Windows.Threading.DispatcherPriority.Normal,
                    new TestContentChange(setButtonText)
                    );
                //setButtonText(message);
            });

        }

        private void testSendButton_Click(object sender, RoutedEventArgs e)
        {
            ISubscriber sub = redis.GetSubscriber();
            sub.Publish("result","\"message " + " @ " + DateTime.Now.ToLongTimeString()+"\"");
        }
    }
}
