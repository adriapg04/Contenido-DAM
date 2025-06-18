using DI.Models;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Windows;

namespace DI.Views
{
    public partial class MainWindow : Window, INotifyPropertyChanged
    {
        public ObservableCollection<Product> Products { get; set; }
        public ObservableCollection<TaskModel> Tasks { get; set; }

        public MainWindow()
        {
            InitializeComponent();

            // Inicializar la lista de productos
            Products = new ObservableCollection<Product>()
            {
                new Product() { Name = "Teclado", Price = 120, ImageSource = "imagenes/teclado.png" },
                new Product() { Name = "Mouse", Price = 80, ImageSource = "imagenes/mouse.png" }
            };

            // Inicializar la lista de tareas
            Tasks = new ObservableCollection<TaskModel>()
            {
                new TaskModel() { Name = "Terminar presentación", IsCompleted = false },
                new TaskModel() { Name = "Enviar correo", IsCompleted = true }
            };

            DataContext = this;
        }

        public event PropertyChangedEventHandler PropertyChanged;
        protected void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
