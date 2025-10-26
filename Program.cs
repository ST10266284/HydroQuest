
using HydroQuestApi.Data;
using HydroQuestApi.Services;
using Microsoft.EntityFrameworkCore;

namespace HydroQuestApi
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            builder.Services.AddDbContext<AppDbContext>(options =>
                options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));
            builder.Services.AddScoped<UserService>();
            builder.Services.AddScoped<HydrationService>();
            builder.Services.AddScoped<GamificationService>();
            builder.Services.AddControllers();
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();

            var app = builder.Build();

            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            app.UseSwagger();
            app.UseSwaggerUI();

            app.UseHttpsRedirection();
            app.UseAuthorization();
            app.MapControllers();
            app.MapGet("/", () => "HydroQuest API is running on Azure");
            app.Run();
        }
    }
}
