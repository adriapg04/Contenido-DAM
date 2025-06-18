using System.Windows;
using System.Windows.Controls;

namespace ProyectoWPF.Controls
{
    public partial class ProductCard : UserControl
    {
        public ProductCard()
        {
            InitializeComponent();
            DataContext = this;
        }

        private void InitializeComponent()
        {
            throw new NotImplementedException();
        }

        public string ProductName
        {
            get { return (string)GetValue(ProductNameProperty); }
            set { SetValue(ProductNameProperty, value); }
        }

        public static readonly DependencyProperty ProductNameProperty =
            DependencyProperty.Register("ProductName", typeof(string), typeof(ProductCard));

        public decimal Price
        {
            get { return (decimal)GetValue(PriceProperty); }
            set { SetValue(PriceProperty, value); }
        }

        public static readonly DependencyProperty PriceProperty =
            DependencyProperty.Register("Price", typeof(decimal), typeof(ProductCard));

        public string ImageSource
        {
            get { return (string)GetValue(ImageSourceProperty); }
            set { SetValue(ImageSourceProperty, value); }
        }

        public static readonly DependencyProperty ImageSourceProperty =
            DependencyProperty.Register("ImageSource", typeof(string), typeof(ProductCard));
    }
}
