using System;
using System.ComponentModel;
using System.Windows;
using MySql.Data.MySqlClient;

namespace Ejercicio4
{
    public partial class MainWindow : Window
    {
        // Opción A: root sin contraseña + AllowPublicKeyRetrieval + desactivar SSL
        private string connectionString =
            "Server=localhost;Database=miapp;Uid=root;Pwd=;SslMode=None;AllowPublicKeyRetrieval=True;";

        public MainWindow()
        {
            InitializeComponent();
        }

        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            try
            {
                using var conn = new MySqlConnection(connectionString);
                conn.Open();
                lblInfo.Text = "✅ Conexión MySQL exitosa";
            }
            catch (Exception ex)
            {
                lblInfo.Text = $"❌ Error conexión: {ex.Message}";
            }
        }

        private void Window_Closing(object sender, CancelEventArgs e)
        {
            string usr = txtUsuario.Text?.Trim();
            if (string.IsNullOrEmpty(usr)) return;

            GuardarValorBD(usr, "VentanaWidth",  this.Width.ToString());
            GuardarValorBD(usr, "VentanaHeight", this.Height.ToString());
        }

        private void txtUsuario_LostFocus(object sender, RoutedEventArgs e)
        {
            string usr = txtUsuario.Text?.Trim();
            if (string.IsNullOrEmpty(usr)) return;

            CargarConfigUsuario(usr);
        }

        public string LeerValorBD(string usuario, string clave)
        {
            try
            {
                using var conn = new MySqlConnection(connectionString);
                conn.Open();
                string sql = "SELECT valor FROM configuraciones WHERE usuario=@u AND clave=@c";
                using var cmd = new MySqlCommand(sql, conn);
                cmd.Parameters.AddWithValue("@u", usuario);
                cmd.Parameters.AddWithValue("@c", clave);
                object resultado = cmd.ExecuteScalar();
                return resultado?.ToString();
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error al leer de BD: {ex.Message}",
                                "Error MySQL",
                                MessageBoxButton.OK,
                                MessageBoxImage.Error);
                return null;
            }
        }

        public void GuardarValorBD(string usuario, string clave, string valor)
        {
            try
            {
                using var conn = new MySqlConnection(connectionString);
                conn.Open();
                string sql = @"
  INSERT INTO configuraciones (usuario, clave, valor)
  VALUES (@u, @c, @v)
  ON DUPLICATE KEY
    UPDATE valor = @v;";
                using var cmd = new MySqlCommand(sql, conn);
                cmd.Parameters.AddWithValue("@u", usuario);
                cmd.Parameters.AddWithValue("@c", clave);
                cmd.Parameters.AddWithValue("@v", valor);
                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error al guardar en BD: {ex.Message}",
                                "Error MySQL",
                                MessageBoxButton.OK,
                                MessageBoxImage.Error);
            }
        }

        private void CargarConfigUsuario(string usuario)
        {
            string wStr = LeerValorBD(usuario, "VentanaWidth");
            string hStr = LeerValorBD(usuario, "VentanaHeight");

            bool okW = double.TryParse(wStr, out double w);
            bool okH = double.TryParse(hStr, out double h);

            if (okW && okH)
            {
                this.Width  = w;
                this.Height = h;
                lblInfo.Text = $"✅ Cargada config de '{usuario}': Width={w}, Height={h}";
            }
            else
            {
                lblInfo.Text = $"⚠️ No hay config previa para '{usuario}'.";
            }
        }
    }
}
